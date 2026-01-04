// 백엔드(Java)의 NxNotice 클래스와 짝을 맞춥니다.
export interface NxNotice {
    noticeId: number;
    title: string;
    content: string;
    viewCount: number;
    regDt: string;
}