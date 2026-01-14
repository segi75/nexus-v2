<template>
  <div class="p-6">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold text-gray-800 dark:text-white">공지사항</h1>
      <NxButton label="글쓰기" @click="goCreate" />
    </div>

    <div class="flex gap-2 mb-6 p-4 bg-white rounded-lg shadow dark:bg-gray-800">
      <div class="w-32">
        <select v-model="searchState.searchType" class="w-full p-2 border rounded dark:bg-gray-700 dark:text-white">
          <option value="TITLE">제목</option>
          <option value="CONTENT">내용</option>
        </select>
      </div>
      <div class="flex-1">
        <NxInput v-model="searchState.keyword" placeholder="검색어를 입력하세요" @keyup.enter="onSearch" />
      </div>
      <NxButton label="검색" @click="onSearch" />
    </div>

    <div v-if="noticeData && noticeData.content" class="bg-white rounded-lg shadow overflow-hidden dark:bg-gray-800">
      <table class="w-full text-left border-collapse">
        <thead class="bg-gray-50 dark:bg-gray-700">
          <tr>
            <th class="p-4 font-semibold text-gray-600 dark:text-gray-200">번호</th>
            <th class="p-4 font-semibold text-gray-600 dark:text-gray-200">제목</th>
            <th class="p-4 font-semibold text-gray-600 dark:text-gray-200">작성자</th>
            <th class="p-4 font-semibold text-gray-600 dark:text-gray-200">등록일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="notice in noticeData.content" :key="notice.noticeId" 
              class="border-t hover:bg-gray-50 dark:border-gray-700 dark:hover:bg-gray-700 cursor-pointer"
              @click="goDetail(notice.noticeId)">
            <td class="p-4">{{ notice.noticeId }}</td>
            <td class="p-4 font-medium">{{ notice.title }}</td>
            <td class="p-4 text-gray-500">{{ notice.regId }}</td>
            <td class="p-4 text-gray-500">{{ notice.regDt }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else-if="pending" class="p-8 text-center text-gray-500">
      로딩 중...
    </div>
    <div v-else class="p-8 text-center text-gray-500">
      데이터가 없습니다.
    </div>

    <div class="flex justify-center gap-2 mt-6">
      <button 
        :disabled="searchState.page <= 1"
        @click="searchState.page--"
        class="px-3 py-1 border rounded disabled:opacity-50 hover:bg-gray-100"
      >
        이전
      </button>
      <span class="px-3 py-1">{{ searchState.page }}</span>
      <button 
        @click="searchState.page++"
        class="px-3 py-1 border rounded hover:bg-gray-100"
      >
        다음
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth';

const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();
const config = useRuntimeConfig();

// 검색 조건 상태
const searchState = reactive({
  page: Number(route.query.page) || 1,
  size: 10,
  searchType: (route.query.searchType as string) || 'TITLE',
  keyword: (route.query.keyword as string) || ''
});

// [최종 수정] 401 및 타입 에러 완전 해결 코드
const { data: noticeData, pending, refresh, error } = await useFetch<any>('/api/v1/notices', {
  baseURL: config.public.apiBase as string,
  params: searchState,
  
  // ★ 중요 1: 서버 사이드 렌더링 끄기 (브라우저 저장소 접근 위함)
  server: false,

  // ★ 중요 2: headers 대신 onRequest 사용 (타입 에러 해결 + 확실한 토큰 주입)
  onRequest({ options }) {
    // 스토어에 토큰이 있을 때만 헤더에 추가
    if (authStore.token) {
      options.headers = options.headers || {};
      // 'as any'를 사용하여 TypeScript의 복잡한 타입 체크를 우회하고 강제 주입
      (options.headers as any).Authorization = `Bearer ${authStore.token}`;
    }
  },

  watch: [searchState],

  onResponseError({ response }) {
    if (response.status === 401) {
      // 토큰이 없거나 만료되었을 때
      authStore.token = null; 
      alert('로그인이 필요합니다.');
      router.push('/login');
    }
  }
});

// ... 나머지 함수들 (onSearch, goDetail, goCreate) 그대로 유지 ...
const onSearch = () => { searchState.page = 1; };
const goDetail = (id: number) => { router.push(`/notice/${id}`); };
const goCreate = () => { router.push('/notice/create'); };
</script>