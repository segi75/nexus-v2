<script setup lang="ts">
// 옵션 데이터 타입 정의 ({ label: "제목", value: "TITLE" })
interface Option {
  label: string;
  value: string | number;
}

defineProps<{
  modelValue: string | number;
  options: Option[];
  label?: string;
}>();

const emit = defineEmits(['update:modelValue']);

const updateValue = (e: Event) => {
  const target = e.target as HTMLSelectElement;
  emit('update:modelValue', target.value);
};
</script>

<template>
  <div class="nx-select-wrapper">
    <label v-if="label" class="nx-label">{{ label }}</label>
    
    <div class="select-container">
      <select 
        :value="modelValue" 
        @change="updateValue"
        class="nx-select-field"
      >
        <option 
          v-for="opt in options" 
          :key="opt.value" 
          :value="opt.value"
        >
          {{ opt.label }}
        </option>
      </select>
      <span class="arrow-icon">▼</span>
    </div>
  </div>
</template>

<style scoped>
.nx-select-wrapper {
  display: flex;
  flex-direction: column;
  gap: 6px;
  width: 100%;
}

.nx-label {
  font-size: 0.9rem;
  font-weight: 600;
  color: #4b5563;
}

.select-container {
  position: relative;
  width: 100%;
}

.nx-select-field {
  height: 40px; /* Input과 높이 통일 */
  width: 100%;
  padding: 0 30px 0 12px; /* 오른쪽 화살표 공간 확보 */
  border: 1px solid #d1d5db;
  border-radius: 4px;
  font-size: 0.95rem;
  appearance: none; /* 기본 브라우저 스타일 제거 */
  background-color: white;
  cursor: pointer;
}

.nx-select-field:focus {
  outline: none;
  border-color: var(--nx-primary, #3B82F6);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.arrow-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 0.7rem;
  color: #6b7280;
  pointer-events: none; /* 클릭 통과 */
}
</style>