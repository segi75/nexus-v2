import { fileURLToPath } from 'url'

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  // 1. 모듈 등록 (이 부분이 누락되어 에러가 발생한 것입니다)
  modules: ['@nuxtjs/tailwindcss'],

  srcDir: 'app',

  alias: {
    '~/types': fileURLToPath(new URL('./types', import.meta.url))
  },
  components: [
    {
      path: '~/components',
      pathPrefix: false, // ★ 핵심: 폴더 이름(atoms 등)을 컴포넌트 이름에 붙이지 않음
    },
  ],

  // 2. 이제 TypeScript가 'tailwindcss' 속성을 올바르게 인식합니다.
  tailwindcss: {
    exposeConfig: true,
    viewer: true,
  }
})