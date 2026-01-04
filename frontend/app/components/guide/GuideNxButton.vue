<script setup lang="ts">
// 공통 복사 로직 가져오기
const { copiedId, copyCode } = useGuideCopy();
const isLoading = ref(false);

const startLoading = () => {
  isLoading.value = true;
  setTimeout(() => isLoading.value = false, 2000);
};
</script>

<template>
  <section class="guide-section">
    <div class="card basic-usage">
      <div class="card-header">
        <h3>🚀 기본 사용법 (Basic Usage)</h3>
        <button 
          class="copy-btn-large" 
          @click="copyCode(`<NxButton label='저장하기' variant='primary' />`, 'btn-basic')"
        >
          {{ copiedId === 'btn-basic' ? '✅ 복사완료!' : '📋 코드 복사하기' }}
        </button>
      </div>

      <div class="usage-row">
        <div class="preview-area">
          <NxButton label="저장하기" variant="primary" />
        </div>
        <div class="code-area">
&lt;NxButton 
  label="저장하기" 
  variant="primary" 
/&gt;
        </div>
      </div>
    </div>

    <div class="card detail-usage">
      <h3>🎨 1. 색상 테마 (variant)</h3>
      <table class="guide-table">
        <thead>
          <tr>
            <th width="150">미리보기</th>
            <th>설명</th>
            <th>코드 (클릭하여 복사)</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><NxButton label="Primary" variant="primary" /></td>
            <td>주요 액션</td>
            <td>
              <button class="code-copy-row" @click="copyCode(`<NxButton label='저장' variant='primary' />`, 'btn-pri')">
                <code>variant="primary"</code>
                <span class="icon">{{ copiedId === 'btn-pri' ? '✅' : '📋' }}</span>
              </button>
            </td>
          </tr>
          <tr>
            <td><NxButton label="Secondary" variant="secondary" /></td>
            <td>보조 액션</td>
            <td>
              <button class="code-copy-row" @click="copyCode(`<NxButton label='취소' variant='secondary' />`, 'btn-sec')">
                <code>variant="secondary"</code>
                <span class="icon">{{ copiedId === 'btn-sec' ? '✅' : '📋' }}</span>
              </button>
            </td>
          </tr>
          <tr>
            <td><NxButton label="Danger" variant="danger" /></td>
            <td>위험/삭제</td>
            <td>
              <button class="code-copy-row" @click="copyCode(`<NxButton label='삭제' variant='danger' />`, 'btn-dan')">
                <code>variant="danger"</code>
                <span class="icon">{{ copiedId === 'btn-dan' ? '✅' : '📋' }}</span>
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="divider"></div>

      <h3>⚡ 2. 상태 및 기능</h3>
      <div class="usage-row">
        <div class="preview-area col-view">
          <div class="row-item">
            <NxButton :label="isLoading ? '저장 중...' : '로딩 테스트'" :loading="isLoading" @click="startLoading" />
            <span class="desc"><code>loading</code> 속성</span>
          </div>
          <div class="row-item">
            <NxButton label="비활성화" disabled />
            <span class="desc"><code>disabled</code> 속성</span>
          </div>
          <div class="row-item">
            <NxButton variant="primary">
              <template #prepend>🔍</template> 아이콘
            </NxButton>
            <span class="desc">Slot 활용</span>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* 가이드 컴포넌트 전용 스타일 */
.guide-section { width: 100%; }
.card { background: white; border: 1px solid #dee2e6; border-radius: 8px; padding: 30px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.03); }
.card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #eee; }
.card h3 { margin: 0; font-size: 1.1rem; color: #495057; }

/* 복사 버튼 */
.copy-btn-large { background: #212529; color: #fff; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; font-weight: bold; transition: all 0.2s; }
.copy-btn-large:hover { background: #343a40; }

/* 코드 영역 */
.usage-row { display: flex; gap: 20px; align-items: stretch; margin-top: 15px; }
.preview-area { flex: 1; display: flex; justify-content: center; align-items: center; background: #f8f9fa; padding: 20px; border-radius: 6px; border: 1px dashed #ced4da; }
.code-area { flex: 1.5; background: #282c34; color: #abb2bf; padding: 15px; border-radius: 6px; font-family: 'Consolas', monospace; font-size: 0.9rem; white-space: pre; overflow-x: auto; }

/* 테이블 */
.guide-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
.guide-table th { text-align: left; padding: 12px; background: #f8f9fa; border-bottom: 2px solid #dee2e6; font-weight: 600; color: #555; }
.guide-table td { padding: 12px; border-bottom: 1px solid #f1f3f5; vertical-align: middle; }

/* 코드 복사 행 */
.code-copy-row { display: flex; align-items: center; justify-content: space-between; width: 100%; background: #f8f9fa; border: 1px solid #e9ecef; padding: 8px 12px; border-radius: 4px; cursor: pointer; text-align: left; font-family: inherit; color: inherit; }
.code-copy-row:hover { background: #e7f5ff; border-color: #74c0fc; }
.code-copy-row code { color: #c92a2a; font-family: 'Consolas', monospace; font-weight: bold; }

.divider { margin: 30px 0; border-top: 1px solid #eee; }
.col-view { flex-direction: column; align-items: flex-start; gap: 15px; }
.row-item { display: flex; align-items: center; gap: 15px; width: 100%; }
.desc { font-size: 0.85rem; color: #888; }
</style>