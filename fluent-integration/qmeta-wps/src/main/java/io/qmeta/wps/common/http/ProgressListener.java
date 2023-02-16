package io.qmeta.wps.common.http;

public abstract class ProgressListener {
    /**
     * 返回false则取消上传/下载
     */
    public abstract boolean onProgress(long bytes, long total);

    public long getProgressInterval() {
        return 500; // default
    }
    
    public static final class Offset extends ProgressListener {
        private final ProgressListener relay;
        private final long bytesOffset;
        private final long adjustedTotal;
        
        public Offset(final ProgressListener relay, final long bytesOffset, final long adjustedTotal) {
            this.relay = relay;
            this.bytesOffset = bytesOffset;
            this.adjustedTotal = adjustedTotal;
        }
        
        @Override
        public boolean onProgress(long bytes, long total) {
            return relay.onProgress(bytesOffset + bytes, adjustedTotal);
        }
        
        @Override
        public long getProgressInterval() {
            return relay.getProgressInterval();
        }
    }
}
