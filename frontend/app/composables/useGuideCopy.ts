// 복사 기능을 담당하는 훅(Hook)
export const useGuideCopy = () => {
    const copiedId = ref<string | null>(null);

    const copyCode = (code: string, id: string) => {
        navigator.clipboard.writeText(code).then(() => {
            copiedId.value = id;
            setTimeout(() => {
                copiedId.value = null;
            }, 1500);
        });
    };

    return {
        copiedId,
        copyCode
    };
};