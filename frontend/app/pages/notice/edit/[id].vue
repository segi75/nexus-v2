<script setup lang="ts">
import type { NxNotice } from '~/types/NxNotice';
import type { NxResponse } from '~/types/NxResponse'; // ★ 타입 임포트 추가

const route = useRoute();
const noticeId = route.params.id;

// 1. 수정할 데이터 담을 변수
const form = ref({
  title: '',
  content: ''
});

// 2. 기존 데이터 불러오기 (표준 응답 객체 NxResponse 적용)
const { data: response, error: fetchError } = await useFetch<NxResponse<NxNotice>>(`http://localhost:8082/api/v1/notices/${noticeId}`);

// 데이터가 로드되면 폼에 채워넣기 (포장 뜯기: response.data)
if (response.value && response.value.data) {
  form.value.title = response.value.data.title;
  form.value.content = response.value.data.content;
}

// 3. 수정 완료 (PUT 요청)
const updateNotice = async () => {
  // 프런트엔드 검사 (선택 사항 - 백엔드 테스트를 위해 주석 처리 가능)
  /*
  if (!form.value.title || !form.value.content) {
    alert('제목과 내용을 모두 입력해주세요.');
    return;
  }
  */

  if (!confirm('수정하시겠습니까?')) return;

  try {
    // ★ 핵심 수정: useFetch -> $fetch 사용 (이벤트 핸들러 내부)
    // 표준 응답(NxResponse)을 받지만, PUT은 바디가 없으므로 제네릭 생략 가능
    await $fetch(`http://localhost:8082/api/v1/notices/${noticeId}`, {
      method: 'PUT', 
      body: form.value
    });

    // 성공 시
    alert('수정되었습니다!');
    navigateTo(`/notice/${noticeId}`); // 상세 페이지로 이동

  } catch (err: any) {
    // ★ 핵심 수정: 백엔드 유효성 검사 에러 메시지 표시
    if (err.response && err.response._data) {
      const serverMsg = err.response._data.message;
      alert(`수정 실패: ${serverMsg}`);
    } else {
      alert('수정 중 오류가 발생했습니다.');
    }
    console.error(err);
  }
};

const goBack = () => navigateTo(`/notice/${noticeId}`);
</script>

<template>
  <div class="page-container">
    <h1 class="page-title">🛠️ 게시글 수정</h1>

    <div v-if="fetchError" class="error-msg">
      게시글 정보를 불러올 수 없습니다.
    </div>

    <div v-else>
      <div class="form-group">
        <label>제목</label>
        <input 
          v-model="form.title" 
          type="text" 
          class="nx-input" 
          placeholder="제목을 입력하세요"
        >
      </div>

      <div class="form-group">
        <label>내용</label>
        <textarea 
          v-model="form.content" 
          class="nx-textarea" 
          rows="10"
          placeholder="내용을 입력하세요"
        ></textarea>
      </div>

      <div class="btn-group">
        <button @click="goBack" class="cancel-btn">취소</button>
        <button @click="updateNotice" class="save-btn">수정 완료</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 0 20px;
}
.page-title {
  font-size: 2rem;
  color: #333;
  margin-bottom: 30px;
  border-bottom: 2px solid #eee;
  padding-bottom: 15px;
}
.form-group { margin-bottom: 20px; }
.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 8px;
  color: #555;
}
.nx-input, .nx-textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
}
.nx-input:focus, .nx-textarea:focus {
  border-color: var(--nx-primary, #3B82F6);
  outline: none;
}
.btn-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}
.save-btn {
  /* 색상 Fallback 적용 */
  background-color: var(--nx-primary, #3B82F6); 
  color: white;
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}
.cancel-btn {
  background-color: #f0f0f0;
  color: #333;
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}
.save-btn:hover { opacity: 0.9; }
.cancel-btn:hover { background-color: #e0e0e0; }

.error-msg {
  color: red;
  text-align: center;
  margin-top: 20px;
}
</style>