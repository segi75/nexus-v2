<template>
  <div class="flex min-h-screen items-center justify-center bg-gray-100 dark:bg-gray-900">
    <div class="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-md dark:bg-gray-800">
      
      <div class="text-center">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white">NEXUS v3.0</h1>
        <p class="mt-2 text-sm text-gray-500 dark:text-gray-400">시스템 접근을 위해 로그인해주세요.</p>
      </div>

      <form @submit.prevent="handleLogin" class="space-y-6">
        <div>
          <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">아이디</label>
          <NxInput v-model="form.userId" placeholder="ID를 입력하세요" required />
        </div>

        <div>
          <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">비밀번호</label>
          <NxInput v-model="form.userPw" type="password" placeholder="비밀번호를 입력하세요" required />
        </div>

        <div v-if="errorMsg" class="p-3 text-sm text-red-600 bg-red-100 rounded-lg">
          {{ errorMsg }}
        </div>

        <NxButton type="submit" block :loading="loading" label="로그인" />
      </form>
      
      <div class="text-center text-xs text-gray-400">
        Unauthorized access is strictly prohibited.
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// 1. 레이아웃 설정: 로그인 페이지는 메뉴바(Sidebar)가 필요 없으므로 layout: false
definePageMeta({
  layout: false
});

// 2. Pinia 스토어 및 라우터 사용
const authStore = useAuthStore();
const router = useRouter();

// 3. 상태 관리
const form = reactive({ userId: '', userPw: '' });
const loading = ref(false);
const errorMsg = ref('');

// 4. 로그인 핸들러
const handleLogin = async () => {
  loading.value = true;
  errorMsg.value = '';

  try {
    // Pinia Store의 login 액션 호출
    await authStore.login(form.userId, form.userPw);
    
    // 성공 시 공지사항 목록으로 이동
    router.push('/notice'); 
  } catch (err: any) {
    // 실패 시 에러 메시지 출력
    errorMsg.value = err.message || '로그인 중 오류가 발생했습니다.';
  } finally {
    loading.value = false;
  }
};
</script>