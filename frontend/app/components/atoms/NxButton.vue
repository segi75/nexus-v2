<script setup lang="ts">
// 버튼의 모든 가능한 옵션을 정의 (확장성 고려)
interface Props {
  label?: string;            // 버튼 텍스트 (슬롯 사용 시 생략 가능)
  variant?: 'primary' | 'secondary' | 'success' | 'warning' | 'danger' | 'dark' | 'light';
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
  type?: 'button' | 'submit' | 'reset'; // form 태그 대응
  
  // --- 확장 스타일 옵션 ---
  outline?: boolean;         // 배경 없이 테두리만 (예: 취소 버튼)
  rounded?: boolean;         // 둥근 알약 모양 (Pill shape)
  block?: boolean;           // 가로 너비 100% 채우기
  
  // --- 상태 옵션 ---
  loading?: boolean;         // 로딩 스피너 표시 (클릭 방지)
  disabled?: boolean;        // 비활성화
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'primary',
  size: 'md',
  type: 'button',
  outline: false,
  rounded: false,
  block: false,
  loading: false,
  disabled: false,
});

const emit = defineEmits(['click']);

// 클릭 핸들러 (로딩 중이거나 비활성화면 클릭 무시)
const handleClick = (event: MouseEvent) => {
  if (props.loading || props.disabled) {
    event.preventDefault();
    return;
  }
  emit('click', event);
};
</script>

<template>
  <button
    class="nx-btn"
    :class="[
      `variant-${variant}`,
      `size-${size}`,
      { 'is-outline': outline },
      { 'is-rounded': rounded },
      { 'is-block': block },
      { 'is-loading': loading }
    ]"
    :type="type"
    :disabled="disabled || loading"
    @click="handleClick"
  >
    <span v-if="loading" class="spinner"></span>

    <span v-if="$slots.prepend && !loading" class="icon-prepend">
      <slot name="prepend"></slot>
    </span>

    <span :class="{ 'invisible': loading }"> <slot>{{ label }}</slot>
    </span>

    <span v-if="$slots.append && !loading" class="icon-append">
      <slot name="append"></slot>
    </span>
  </button>
</template>

<style scoped>
/* --- 1. Base Structure (공통 구조) --- */
.nx-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  position: relative;
  border: 1px solid transparent; /* 테두리 공간 확보 */
  font-family: inherit;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  white-space: nowrap;
  gap: 6px; /* 아이콘과 텍스트 간격 */
  user-select: none;
}

/* 꽉 찬 버튼 (Block) */
.nx-btn.is-block {
  display: flex;
  width: 100%;
}

/* 둥근 버튼 (Pill) */
.nx-btn.is-rounded {
  border-radius: 9999px;
}

/* 기본 모서리 */
.nx-btn:not(.is-rounded) {
  border-radius: 6px;
}

/* 비활성화 상태 */
.nx-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none !important; /* 클릭 효과 제거 */
}

/* 클릭 효과 (Active) */
.nx-btn:active:not(:disabled) {
  transform: translateY(1px);
  filter: brightness(0.95);
}

/* --- 2. Sizes (크기 정의) --- */
.size-xs { height: 24px; padding: 0 8px; font-size: 0.75rem; }
.size-sm { height: 32px; padding: 0 12px; font-size: 0.85rem; }
.size-md { height: 40px; padding: 0 20px; font-size: 0.95rem; } /* 기본 */
.size-lg { height: 48px; padding: 0 28px; font-size: 1.1rem; }
.size-xl { height: 56px; padding: 0 36px; font-size: 1.25rem; }

/* --- 3. Variants (색상 테마) --- */
/* CSS 변수 매핑 (디자인 토큰 활용) */
.nx-btn {
  --btn-bg: #e5e7eb;
  --btn-text: #374151;
  --btn-border: #d1d5db;
}

/* Primary */
.variant-primary { --btn-bg: var(--nx-primary, #3B82F6); --btn-text: white; --btn-border: var(--nx-primary, #3B82F6); }
/* Secondary */
.variant-secondary { --btn-bg: #6B7280; --btn-text: white; --btn-border: #6B7280; }
/* Success */
.variant-success { --btn-bg: #10B981; --btn-text: white; --btn-border: #10B981; }
/* Danger */
.variant-danger { --btn-bg: #EF4444; --btn-text: white; --btn-border: #EF4444; }
/* Warning */
.variant-warning { --btn-bg: #F59E0B; --btn-text: white; --btn-border: #F59E0B; }
/* Dark */
.variant-dark { --btn-bg: #1F2937; --btn-text: white; --btn-border: #1F2937; }

/* --- 4. Style Logic (Fill vs Outline) --- */

/* [Fill Style] (기본: 배경색이 칠해진 상태) */
.nx-btn:not(.is-outline) {
  background-color: var(--btn-bg);
  color: var(--btn-text);
  border-color: var(--btn-border);
}
.nx-btn:not(.is-outline):hover:not(:disabled) {
  opacity: 0.9; /* 살짝 투명하게 */
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

/* [Outline Style] (배경 투명, 테두리만 색상) */
.nx-btn.is-outline {
  background-color: transparent;
  color: var(--btn-bg); /* 배경색을 글자색으로 사용 */
  border-color: var(--btn-bg);
}
.nx-btn.is-outline:hover:not(:disabled) {
  background-color: var(--btn-bg); /* 호버 시 색 채움 */
  color: var(--btn-text);
}

/* --- 5. Loading Spinner (CSS Only) --- */
.spinner {
  width: 1em;
  height: 1em;
  border: 2px solid currentColor;
  border-right-color: transparent;
  border-radius: 50%;
  animation: spin 0.75s linear infinite;
  margin-right: 6px;
}
.invisible {
  visibility: hidden; /* 로딩 중 텍스트 자리 확보하되 숨김 */
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>