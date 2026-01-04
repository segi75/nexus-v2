<script setup lang="ts">
// 입력 데이터를 담을 반응형 객체
const form = ref({
  title: '',
  content: ''
});

// 저장 버튼 클릭 시 실행
const saveNotice = async () => {
//   if (!form.value.title || !form.value.content) {
//     alert('제목과 내용을 모두 입력해주세요.');
//     return;
//   }

try {
    // useFetch -> $fetch 로 변경 (버튼 클릭 시에는 이게 정석)
    await $fetch('http://localhost:8082/api/v1/notices', {
      method: 'POST',
      body: form.value
    });

    // 성공 시
    alert('저장되었습니다!');
    navigateTo('/notice');

  } catch (err: any) {
    // 백엔드에서 보낸 에러 메시지 보여주기
    // GlobalExceptionHandler가 보낸 { code, message } 중 message를 꺼냅니다.
    if (err.response && err.response._data) {
      const serverMsg = err.response._data.message;
      alert(`저장 실패: ${serverMsg}`);
    } else {
      alert('저장 중 알 수 없는 오류가 발생했습니다.');
    }
    console.error(err);
  }
};

const goBack = () => navigateTo('/notice');
</script>

<template>
  <div class="page-container">
    <h1 class="page-title">✏️ 게시글 작성</h1>

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
        placeholder="내용을 입력하세요"
        rows="10"
      ></textarea>
    </div>

    <div class="btn-group">
      <button @click="goBack" class="cancel-btn">취소</button>
      <button @click="saveNotice" class="save-btn">저장하기</button>
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
.form-group {
  margin-bottom: 20px;
}
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
  transition: border-color 0.2s;
  box-sizing: border-box; /* 패딩 포함 크기 계산 */
}
.nx-input:focus, .nx-textarea:focus {
  border-color: var(--nx-primary);
  outline: none;
}
.btn-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}

/* 👇 수정 후 (콤마와 색상코드 추가) */
.save-btn {
  /* 색상을 못 가져오면 파란색(#3B82F6)을 보여줘라! */
  background-color: var(--nx-primary, #3B82F6); 
  color: white;
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1rem;
}
.cancel-btn {
  background-color: #f0f0f0;
  color: #333;
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1rem;
}
.save-btn:hover { opacity: 0.9; }
.cancel-btn:hover { background-color: #e0e0e0; }
</style>