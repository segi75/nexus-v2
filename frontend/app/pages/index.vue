<template>
  <div class="home-wrapper">
    <h1>🏠 홈 화면</h1>
    <p>NEXUS 프레임워크 초기 화면입니다.</p>
    
    <div class="test-box">
      <p>테마 엔진 테스트 버튼:</p>
      <button class="nx-btn">Primary Button</button>
    </div>
  </div>
</template>

<style scoped>
.home-wrapper {
  text-align: center;
  margin-top: 50px;
}

.nx-btn {
  /* 수정: var(변수명, 기본값) 형식을 사용하여 DB 연결 전에도 파란색이 나오도록 함 */
  background-color: var(--nx-primary, #3B82F6); 
  color: white;
  padding: 10px 20px;
  border-radius: var(--nx-radius-base, 4px);
  border: none;
  cursor: pointer;
  font-weight: bold;
}

.nx-btn:hover {
  opacity: 0.9;
}
</style>