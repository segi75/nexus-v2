// 백엔드의 ApiResponse<T>와 매핑되는 제네릭 인터페이스
export interface ApiResponse<T> {
    status: string;
    message: string;
    data: T;
}