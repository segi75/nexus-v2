import { fileURLToPath } from 'url'

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  // [핵심] 모듈 등록: tailwindcss 뒤에 pinia 추가
  modules: [
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@pinia-plugin-persistedstate/nuxt',
  ],

  // [CORS 해결을 위한 Proxy 설정]
  nitro: {
    devProxy: {
      // '/api'로 시작하는 요청이 오면 8083 포트로 전달
      '/api': {
        target: 'http://localhost:8083',
        changeOrigin: true,
      }
    }
  },

  runtimeConfig: {
    public: {
      // 프록시를 타야 하므로 이제부터는 도메인을 뺍니다.
      apiBase: '/api'
    }
  },

  srcDir: 'app',


  // (선택) 기본적으로 로컬 스토리지를 사용하도록 설정
  piniaPersistedstate: {
    storage: 'localStorage',
  },

  // [추가] Pinia 설정: stores 폴더 안의 파일들을 자동으로 import (useAuthStore 등)
  pinia: {
    storesDirs: ['./stores/**'],
  },

  alias: {
    '~/types': fileURLToPath(new URL('./types', import.meta.url))
  },

  components: [
    {
      path: '~/components',
      pathPrefix: false,
    },
  ],

  tailwindcss: {
    exposeConfig: true,
    viewer: true,
  }
})