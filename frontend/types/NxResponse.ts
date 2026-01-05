export interface NxResponse<T> {
    code: string;
    message: string;
    data: T;
    timestamp: string;
}