<script setup lang="ts">
import type { NxNotice } from '~/types/NxNotice';
import type { NxResponse } from '~/types/NxResponse'; // ★ 추가된 타입

const route = useRoute();
const noticeId = route.params.id;

// 1. 표준 응답 객체(NxResponse)로 받기
// 변수명을 notice -> response로 변경하여 혼동 방지
const { data: response, error } = await useFetch<NxResponse<NxNotice>>(`http://localhost:8082/api/v1/notices/${noticeId}`);

// 목록으로 돌아가기
const goBack = () => {
  navigateTo('/notice');
};

// 삭제 기능
const deleteNotice = async () => {
  if (!confirm('정말 삭제하시겠습니까?')) return;

  // 삭제 요청도 표준 응답으로 처리
  const { error: delError } = await useFetch(`http://localhost:8082/api/v1/notices/${noticeId}`, {
    method: 'DELETE'
  });

  if (delError.value) {
    alert('삭제 실패!');
  } else {
    alert('삭제되었습니다.');
    navigateTo('/notice');
  }
};

// 수정 페이지로 이동
const goEdit = () => {
  navigateTo(`/notice/edit/${noticeId}`);
};
</script>

<template>
  <div class="page-container" v-if="response && response.data">
    <div class="detail-header">
      <div class="meta-info">
        <span class="badge">공지 #{{ response.data.noticeId }}</span>
        <span class="date">{{ new Date(response.data.regDt).toLocaleDateString() }}</span>
      </div>
      <h1 class="title">{{ response.data.title }}</h1>
    </div>

    <div class="detail-content">
      {{ response.data.content }}
    </div>

    <div class="detail-footer">
      <div class="left-group">
        <span class="view-count">👀 조회수 {{ response.data.viewCount }}</span>
      </div>
      
      <div class="right-group">
        <button @click="goBack" class="btn back-btn">목록</button>
        <button @click="goEdit" class="btn edit-btn">수정</button>
        <button @click="deleteNotice" class="btn del-btn">삭제</button>
      </div>
    </div>
  </div>

  <div v-else-if="error" class="error-msg">
    <p>게시글을 불러올 수 없습니다.</p>
    <button @click="goBack" class="btn back-btn">돌아가기</button>
  </div>
</template>

<style scoped>
.page-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 0 20px;
  animation: fadeIn 0.3s ease-in-out;
}

.detail-header {
  border-bottom: 2px solid #eee;
  padding-bottom: 20px;
  margin-bottom: 30px;
}

.meta-info {
  display: flex;
  gap: 10px;
  align-items: center;
  margin-bottom: 10px;
}

.badge {
  background-color: var(--nx-primary, #3B82F6);
  color: white;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: bold;
}

.date {
  color: #888;
  font-size: 0.9rem;
}

.title {
  font-size: 2rem;
  color: #333;
  margin: 0;
  line-height: 1.3;
}

.detail-content {
  min-height: 200px;
  line-height: 1.8;
  color: #444;
  font-size: 1.1rem;
  white-space: pre-line; /* 줄바꿈 반영 */
}

/* 하단 버튼 그룹 스타일 통합 */
.detail-footer {
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #eee;
  display: flex;
  justify-content: space-between; /* 양끝 정렬 */
  align-items: center;
}

.view-count {
  color: #999;
  font-size: 0.9rem;
}

.btn {
  padding: 10px 20px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-weight: bold;
  margin-left: 8px;
  font-size: 0.95rem;
  transition: opacity 0.2s;
}

.back-btn { 
  background-color: #f0f0f0; 
  color: #333; 
}
.back-btn:hover { background-color: #e0e0e0; }

.edit-btn { 
  background-color: var(--nx-primary, #3B82F6); 
  color: white; 
}
.edit-btn:hover { opacity: 0.9; }

.del-btn { 
  background-color: #ef4444; 
  color: white; 
}
.del-btn:hover { background-color: #dc2626; }

.error-msg {
  text-align: center;
  margin-top: 50px;
  color: #666;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>