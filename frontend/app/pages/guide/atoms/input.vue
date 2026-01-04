<script setup lang="ts">
definePageMeta({ layout: 'guide' });
</script>

<template>
  <div>
    <h1>Input (입력창)</h1>
    <p class="intro">텍스트 데이터를 입력받기 위한 컴포넌트입니다.</p>
    <hr class="divider" />
    <GuideNxInput />
  </div>
</template>

<style scoped>
.intro { color: #666; font-size: 1.1rem; margin-bottom: 30px; }
.divider { margin-bottom: 40px; border: 0; border-top: 1px solid #eee; }
</style>