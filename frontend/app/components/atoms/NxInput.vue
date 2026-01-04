<script setup lang="ts">
// 1. 부모에게 받을 속성(Props) 정의
defineProps<{
  modelValue: string | number; // v-model 연결용
  label?: string;              // 입력창 위에 뜰 라벨 (선택)
  placeholder?: string;        // 힌트 텍스트
  type?: string;               // text, password, date 등 (기본: text)
  error?: string;              // 에러 메시지 (있으면 빨간 테두리)
}>();

// 2. 부모에게 보낼 이벤트(Emits) 정의
const emit = defineEmits(['update:modelValue', 'enter']); // enter 키 이벤트 추가

// 값 변경 시 부모에게 알림
const updateValue = (e: Event) => {
  const target = e.target as HTMLInputElement;
  emit('update:modelValue', target.value);
};
</script>

<template>
  <div class="nx-input-wrapper">
    <label v-if="label" class="nx-label">{{ label }}</label>
    
    <input 
      :type="type || 'text'"
      :value="modelValue"
      :placeholder="placeholder"
      :class="['nx-input-field', { 'has-error': error }]"
      @input="updateValue"
      @keyup.enter="$emit('enter')"
    >
    
    <p v-if="error" class="nx-error-text">{{ error }}</p>
  </div>
</template>

<style scoped>
.nx-input-wrapper {
  display: flex;
  flex-direction: column;
  gap: 6px;
  width: 100%; /* 부모 크기에 맞춤 */
}

.nx-label {
  font-size: 0.9rem;
  font-weight: 600;
  color: #4b5563;
}

.nx-input-field {
  height: 40px; /* 표준 높이 고정 */
  padding: 0 12px;
  border: 1px solid #d1d5db;
  border-radius: 4px;
  font-size: 0.95rem;
  transition: all 0.2s;
  box-sizing: border-box;
  width: 100%;
}

/* 포커스 시 테마 색상(Primary)으로 빛남 */
.nx-input-field:focus {
  outline: none;
  border-color: var(--nx-primary, #3B82F6);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

/* 에러 상태일 때 빨간색 */
.nx-input-field.has-error {
  border-color: #ef4444;
  background-color: #fef2f2;
}

.nx-error-text {
  font-size: 0.8rem;
  color: #ef4444;
  margin: 0;
}
</style>