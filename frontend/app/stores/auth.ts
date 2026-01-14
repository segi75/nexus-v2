import { defineStore } from 'pinia';

interface User {
    userId: string;
    userNm: string;
    roles: string[];
}

export const useAuthStore = defineStore('auth', () => {
    // 1. 상태 (State)
    const token = useCookie<string | null>('nexus_token', {
        maxAge: 60 * 60 * 24,
        watch: true,
        default: () => null,
    });

    const user = useState<User | null>('nexus_user', () => null);

    // 2. 게터 (Getters)
    const isAuthenticated = computed(() => !!token.value);

    // 3. 액션 (Actions)
    async function login(userId: string, userPw: string) {
        try {
            console.log('🚀 로그인 시도:', userId);

            // ★ [수정 1] useFetch -> $fetch 로 변경 (이벤트 핸들러용)
            // ★ [수정 2] URL 앞에 도메인 제거 (Proxy가 처리함)
            // /api/v1/auth/login 으로 요청하면 -> http://localhost:8083/api/v1/auth/login 로 연결됨
            const responseData = await $fetch<any>('/api/v1/auth/login', {
                method: 'POST',
                body: {
                    userId: userId,
                    userPw: userPw,
                    // 백엔드가 혹시 username/password를 찾을까봐 추가로 보냄
                    username: userId,
                    password: userPw
                }
            });

            // $fetch는 에러 발생 시 자동으로 catch 블록으로 넘어갑니다.
            // 따라서 if (error.value) 체크가 필요 없습니다.

            console.log('🔥 백엔드 응답 데이터:', responseData);

            // 토큰 찾기 로직
            let accessToken: string | null = null;

            if (responseData) {
                if (typeof responseData.token === 'string') {
                    accessToken = responseData.token;
                }
                else if (responseData.data && typeof responseData.data.token === 'string') {
                    accessToken = responseData.data.token;
                }
                else if (typeof responseData.accessToken === 'string') {
                    accessToken = responseData.accessToken;
                }
            }

            if (accessToken) {
                console.log('✅ 토큰 추출 성공:', accessToken);
                token.value = accessToken;

                user.value = {
                    userId: userId,
                    userNm: responseData?.userNm || responseData?.data?.userNm || '관리자',
                    roles: []
                };
                return true;
            } else {
                console.error('🚨 토큰 없음. 응답 확인 필요.');
                return false;
            }

        } catch (err: any) {
            console.error('❌ 로그인 요청 실패:', err);
            // 백엔드 에러 메시지 추출 시도
            const errorMessage = err.data?.message || err.message || '로그인 실패';
            alert(errorMessage); // 사용자에게 알림
            throw err;
        }
    }

    function logout() {
        token.value = null;
        user.value = null;
        const router = useRouter();
        router.push('/login');
    }

    return { token, user, isAuthenticated, login, logout };
});