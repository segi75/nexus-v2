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

  // 2. 이제 TypeScript가 'tailwindcss' 속성을 올바르게 인식합니다.
  tailwindcss: {
    exposeConfig: true,
    viewer: true,
  }
})