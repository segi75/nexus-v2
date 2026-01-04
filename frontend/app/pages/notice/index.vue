<script setup lang="ts">
import type { NxNotice } from '~/types/NxNotice';
import type { NxResponse } from '~/types/NxResponse'; 

// API 응답 데이터 내부 구조 (PageResult)
interface PageResult {
  list: NxNotice[];
  totalCount: number;
  currentPage: number;
}

const route = useRoute();
const router = useRouter();

// 1. URL 파라미터와 변수 연결
const page = computed(() => Number(route.query.page) || 1);
const searchType = ref((route.query.searchType as string) || 'TITLE');
const keyword = ref((route.query.keyword as string) || '');

// 2. 데이터 가져오기 (표준 응답 객체 NxResponse 적용)
// 변수명을 data -> response 로 변경하여 헷갈림 방지
const { data: response, status, refresh } = await useFetch<NxResponse<PageResult>>('http://localhost:8082/api/v1/notices', {
  query: { 
    page: page, 
    size: 5, 
    searchType: searchType, 
    keyword: keyword
  },
  watch: [page] // 페이지 번호가 바뀌면 자동 호출
});

// 3. 페이지 변경 함수
const changePage = (newPage: number) => {
  router.push({ 
    query: { 
      ...route.query, // 기존 검색 조건 유지
      page: newPage   // 페이지 번호만 변경
    } 
  });
};

// 4. 검색 실행 함수
const executeSearch = () => {
  router.push({ 
    query: { 
      page: 1, // 검색 시 1페이지로 리셋
      searchType: searchType.value, 
      keyword: keyword.value 
    } 
  });
  // 데이터 갱신 보장
  setTimeout(() => refresh(), 50);
};
</script>

<template>
  <div class="page-container">
    <h1 class="page-title">📢 공지사항 (Enterprise)</h1>

    <div class="search-box">
      <div style="width: 120px;"> <NxSelect 
          v-model="searchType" 
          :options="[
            { label: '제목', value: 'TITLE' },
            { label: '내용', value: 'CONTENT' }
          ]" 
        />
    </div>
      
    <div style="flex: 1;"> <NxInput 
        v-model="keyword" 
        placeholder="검색어를 입력하세요" 
        @enter="executeSearch"
      />
    </div>
      
      <NxButton label="검색" variant="dark" @click="executeSearch" />
  
      <NxButton variant="primary" @click="navigateTo('/notice/create')">
        ✍️ 글쓰기
      </NxButton>
    </div>

    <div v-if="status === 'pending'" class="loading">
      데이터를 불러오는 중입니다...
    </div>

    <div v-else-if="response && response.data && response.data.list.length > 0">
      <div 
        v-for="item in response.data.list" 
        :key="item.noticeId" 
        class="notice-card"
        @click="navigateTo(`/notice/${item.noticeId}`)"
      >
        <div class="card-header">
          <span class="badge">No. {{ item.noticeId }}</span>
          <span class="date">{{ new Date(item.regDt).toLocaleDateString() }}</span>
        </div>
        <h2 class="title">{{ item.title }}</h2>
      </div>

      <div class="pagination">
        <button :disabled="page <= 1" @click="changePage(page - 1)">이전</button>
        
        <span class="page-info">
          Page {{ page }} / Total {{ response.data.totalCount }}
        </span>
        
        <button 
          :disabled="response.data.list.length < 5" 
          @click="changePage(page + 1)"
        >다음</button>
      </div>
    </div>

    <div v-else class="empty-state">
      등록된 공지사항이 없습니다.
    </div>
  </div>
</template>

<style scoped>
/* --- 기본 레이아웃 --- */
.page-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 0 20px;
}

.page-title {
  font-size: 2rem;
  font-weight: bold;
  color: var(--nx-primary, #333);
  margin-bottom: 30px;
  border-bottom: 2px solid var(--nx-primary, #333);
  padding-bottom: 15px;
}

/* --- 검색 박스 (Toolbar) --- */
.search-box {
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
  justify-content: flex-end; /* 우측 정렬 */
  gap: 8px; /* 요소 간격 */
  margin-bottom: 20px;
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 8px;
}

/* 입력 도구 높이 통일 (40px) */
.nx-select, 
.nx-input, 
.nx-btn {
  height: 40px;
  box-sizing: border-box;
  vertical-align: middle;
  font-size: 0.95rem;
}

.nx-select {
  padding: 0 30px 0 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  cursor: pointer;
}

.nx-input {
  width: 250px;
  padding: 0 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.nx-input:focus, 
.nx-select:focus {
  border-color: var(--nx-primary, #3B82F6);
  outline: none;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

/* --- 버튼 스타일 --- */
.search-btn {
  background-color: #333;
  color: white;
  border: none;
  padding: 0 20px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.2s;
}
.search-btn:hover { background-color: #555; }

.write-btn {
  background-color: var(--nx-primary, #3B82F6);
  color: white;
  border: none;
  padding: 0 20px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
  margin-left: 10px;
}
.write-btn:hover { opacity: 0.9; }

/* --- 카드 리스트 스타일 --- */
.notice-card {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  transition: transform 0.2s, border-color 0.2s;
}

.notice-card:hover {
  transform: translateY(-3px);
  border-color: var(--nx-primary, #3B82F6);
}

.card-header {
  display: flex;
  justify-content: space-between;
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 8px;
}

.title {
  font-size: 1.25rem;
  font-weight: bold;
  margin: 0;
  color: #1a1a1a;
}

/* --- 페이지네이션 --- */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 30px;
}

.pagination button {
  padding: 8px 16px;
  cursor: pointer;
  background: var(--nx-primary, #3B82F6);
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: bold;
}

.pagination button:disabled {
  background: #ccc;
  cursor: not-allowed;
}

/* --- 유틸리티 --- */
.loading, .empty-state {
  text-align: center;
  padding: 50px;
  color: #888;
}

@media (max-width: 768px) {
  .search-box {
    flex-direction: column; /* 가로 정렬 -> 세로 정렬로 변경 */
    align-items: stretch;   /* 너비를 꽉 채우도록 변경 */
    gap: 10px;              /* 간격 조정 */
    height: auto;           /* 높이 자동 조절 */
  }

  /* 입력 도구들을 한 줄 꽉 차게 변경 */
  .nx-select, 
  .nx-input, 
  .nx-btn {
    width: 100%; 
    margin: 0;     /* 기존 마진 제거 */
    height: 45px;  /* 터치하기 편하게 높이 약간 키움 */
  }

  /* 글쓰기 버튼 상단 여백 추가 */
  .write-btn {
    margin-left: 0;
    margin-top: 10px; 
  }
}
</style>