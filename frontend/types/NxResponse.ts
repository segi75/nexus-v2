export interface NxResponse<T> {
    code: string;     // 예: "SUCCESS"
    message: string;  // 예: "OK"
    data: T;          // 실제 알맹이 (제네릭)
}