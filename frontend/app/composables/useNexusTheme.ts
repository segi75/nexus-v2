// composables/useNexusTheme.ts
export const useNexusTheme = () => {
    // ref가 인식이 안 된다면 명시적으로 import (Nuxt가 자동으로 해결해줄 때까지)
    const themeData = ref<any>(null);

    const fetchTheme = async (tokenId: string) => {
        // URL 앞에 'http://'가 누락되지 않았는지 확인하세요.
        const { data, error } = await useFetch(`http://localhost:8082/api/v1/tokens/${tokenId}`);

        if (error.value) {
            console.error('NEXUS [THEME_ERROR]:', error.value);
            return;
        }

        if (data.value) {
            // @ts-ignore (타입 에러 방지용)
            const rawValue = JSON.parse(data.value.tokenValue);
            themeData.value = rawValue;
            injectTheme(rawValue);
        }
    };

    const injectTheme = (values: any) => {
        if (process.server) return; // 서버 사이드 에러 방지
        const root = document.documentElement;

        if (values.colors) {
            Object.entries(values.colors).forEach(([key, val]) => {
                root.style.setProperty(`--nx-${key}`, val as string);
            });
        }
    };

    return { themeData, fetchTheme };
};