<script setup lang="ts">
// 아까 만든 공통 타입 import (경로 주의)
import type { ApiResponse } from '~/types/common'

// API 응답 타입 정의 (TS 사용 시)
interface HelloVO {
  id: number;
  message: string;
  regDt: string;
}

// Spring Boot API 호출 (SSR/CSR 모두 지원하는 useFetch 사용)
const { data: helloList, error } = await useFetch<HelloVO[]>('http://localhost:8082/api/hello')
</script>

<template>
  <div class="container">
    <h1>NEXUS 2.0 Hello World</h1>
    
    <div v-if="error" class="error">
      API 호출 실패: {{ error.message }}
    </div>

    <ul v-else>
      <li v-for="item in helloList" :key="item.id">
        <strong>[{{ item.id }}]</strong> {{ item.message }} 
        <span class="date">({{ item.regDt }})</span>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.container { padding: 20px; font-family: sans-serif; }
.date { color: #888; font-size: 0.9em; margin-left: 10px; }
.error { color: red; font-weight: bold; }
</style>