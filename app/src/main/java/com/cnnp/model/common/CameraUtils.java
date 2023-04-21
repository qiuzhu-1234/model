package com.cnnp.model.common;

import android.util.Log;
import android.util.Size;

import java.util.List;

public class CameraUtils {
    private static final String TAG = "chesihu";
    private static final String PICTURE_RATIO_4_3 = "1.3333";

    public static Size getPreviewSize(List<Size> sources, double targetRatio, Size screenSize, boolean needMatchTargetPanelSize, double aspectTolerance) {

        int panelHeight = Math.min(screenSize.getWidth(), screenSize.getHeight());
        int panelWidth = (int) (targetRatio * panelHeight);
        Size optimalSize = null;
        if (needMatchTargetPanelSize) {
            Log.d(TAG, "ratio mapping panel size: (" + panelWidth + ", " + panelHeight + ")");
            optimalSize = findBestMatchPanelSize(sources, targetRatio, panelWidth, panelHeight, aspectTolerance);
            if (optimalSize != null) {
                return optimalSize;
            }
        }

        double minDiffHeight = Double.MAX_VALUE;
        if (optimalSize == null) {
            Log.d(TAG, "[getPreviewSize] no preview size match the aspect ratio : " + targetRatio + ", then use standard 4:3 for preview");
            targetRatio = Double.parseDouble(PICTURE_RATIO_4_3);
            for (Size size : sources) {
                double ratio = (double) size.getWidth() / size.getHeight();
                if (Math.abs(ratio - targetRatio) > aspectTolerance) {
                    continue;
                }
                if (Math.abs(size.getHeight() - panelHeight) < minDiffHeight) {
                    optimalSize = size;
                    minDiffHeight = Math.abs(size.getHeight() - panelHeight);
                }
            }
        }
        return optimalSize;
    }

    private static Size findBestMatchPanelSize(List<Size> sizes, double targetRatio, int panelWidth, int panelHeight
            , double aspectTolerance) {
        double minDiff;
        double minDiffMax = Double.MAX_VALUE;
        Size bestMatchSize = null;
        for (Size size : sizes) {
            double ratio = (double) size.getWidth() / size.getHeight();
            // filter out the size which not tolerated by target ratio
            if (Math.abs(ratio - targetRatio) <= aspectTolerance) {
                // find the size closest to panel size
                minDiff = Math.abs(size.getHeight() - panelHeight);
                if (minDiff <= minDiffMax) {
                    minDiffMax = minDiff;
                    bestMatchSize = size;
                }
            }
        }
        return bestMatchSize;
    }
}

