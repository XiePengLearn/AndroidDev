package com.xiaoanjujia.home.entities;

/**
 * @Auther: xp
 * @Date: 2020/9/11 21:27
 * @Description:
 */
public class VisitorFaceScoreResponse {

    /**
     * data : {"facePicAnalysisResult":{"glasses":"NO","recommendFaceRect":{"width":0,"x":0,"y":0,"height":0},"gender":"male","facePose":{"colorConfidence":1,"roll":5.715253829956055,"eyeDistance":60.7224006652832,"grayMean":165.064697265625,"pitch":-0.0021992474794387817,"visibleScore":1,"yaw":7.998233795166016,"clearityScore":0.949999988079071},"ageGroup":"YOUNG","faceRect":{"width":0.21306690573692322,"x":0.3645472228527069,"y":0.30906248092651367,"height":0.11936527490615845},"id":0,"faceScore":76,"smile":"NO"},"checkResult":true,"faceScore":76}
     * message : OK
     * status : 1
     */

    private DataBean data;
    private String message;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * facePicAnalysisResult : {"glasses":"NO","recommendFaceRect":{"width":0,"x":0,"y":0,"height":0},"gender":"male","facePose":{"colorConfidence":1,"roll":5.715253829956055,"eyeDistance":60.7224006652832,"grayMean":165.064697265625,"pitch":-0.0021992474794387817,"visibleScore":1,"yaw":7.998233795166016,"clearityScore":0.949999988079071},"ageGroup":"YOUNG","faceRect":{"width":0.21306690573692322,"x":0.3645472228527069,"y":0.30906248092651367,"height":0.11936527490615845},"id":0,"faceScore":76,"smile":"NO"}
         * checkResult : true
         * faceScore : 76
         */

        private FacePicAnalysisResultBean facePicAnalysisResult;
        private boolean checkResult;
        private int faceScore;

        public FacePicAnalysisResultBean getFacePicAnalysisResult() {
            return facePicAnalysisResult;
        }

        public void setFacePicAnalysisResult(FacePicAnalysisResultBean facePicAnalysisResult) {
            this.facePicAnalysisResult = facePicAnalysisResult;
        }

        public boolean isCheckResult() {
            return checkResult;
        }

        public void setCheckResult(boolean checkResult) {
            this.checkResult = checkResult;
        }

        public int getFaceScore() {
            return faceScore;
        }

        public void setFaceScore(int faceScore) {
            this.faceScore = faceScore;
        }

        public static class FacePicAnalysisResultBean {
            /**
             * glasses : NO
             * recommendFaceRect : {"width":0,"x":0,"y":0,"height":0}
             * gender : male
             * facePose : {"colorConfidence":1,"roll":5.715253829956055,"eyeDistance":60.7224006652832,"grayMean":165.064697265625,"pitch":-0.0021992474794387817,"visibleScore":1,"yaw":7.998233795166016,"clearityScore":0.949999988079071}
             * ageGroup : YOUNG
             * faceRect : {"width":0.21306690573692322,"x":0.3645472228527069,"y":0.30906248092651367,"height":0.11936527490615845}
             * id : 0
             * faceScore : 76
             * smile : NO
             */

            private String glasses;
            private RecommendFaceRectBean recommendFaceRect;
            private String gender;
            private FacePoseBean facePose;
            private String ageGroup;
            private FaceRectBean faceRect;
            private int id;
            private int faceScore;
            private String smile;

            public String getGlasses() {
                return glasses;
            }

            public void setGlasses(String glasses) {
                this.glasses = glasses;
            }

            public RecommendFaceRectBean getRecommendFaceRect() {
                return recommendFaceRect;
            }

            public void setRecommendFaceRect(RecommendFaceRectBean recommendFaceRect) {
                this.recommendFaceRect = recommendFaceRect;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public FacePoseBean getFacePose() {
                return facePose;
            }

            public void setFacePose(FacePoseBean facePose) {
                this.facePose = facePose;
            }

            public String getAgeGroup() {
                return ageGroup;
            }

            public void setAgeGroup(String ageGroup) {
                this.ageGroup = ageGroup;
            }

            public FaceRectBean getFaceRect() {
                return faceRect;
            }

            public void setFaceRect(FaceRectBean faceRect) {
                this.faceRect = faceRect;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFaceScore() {
                return faceScore;
            }

            public void setFaceScore(int faceScore) {
                this.faceScore = faceScore;
            }

            public String getSmile() {
                return smile;
            }

            public void setSmile(String smile) {
                this.smile = smile;
            }

            public static class RecommendFaceRectBean {
                /**
                 * width : 0
                 * x : 0
                 * y : 0
                 * height : 0
                 */

                private int width;
                private int x;
                private int y;
                private int height;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }

            public static class FacePoseBean {
                /**
                 * colorConfidence : 1
                 * roll : 5.715253829956055
                 * eyeDistance : 60.7224006652832
                 * grayMean : 165.064697265625
                 * pitch : -0.0021992474794387817
                 * visibleScore : 1
                 * yaw : 7.998233795166016
                 * clearityScore : 0.949999988079071
                 */

                private int colorConfidence;
                private double roll;
                private double eyeDistance;
                private double grayMean;
                private double pitch;
                private int visibleScore;
                private double yaw;
                private double clearityScore;

                public int getColorConfidence() {
                    return colorConfidence;
                }

                public void setColorConfidence(int colorConfidence) {
                    this.colorConfidence = colorConfidence;
                }

                public double getRoll() {
                    return roll;
                }

                public void setRoll(double roll) {
                    this.roll = roll;
                }

                public double getEyeDistance() {
                    return eyeDistance;
                }

                public void setEyeDistance(double eyeDistance) {
                    this.eyeDistance = eyeDistance;
                }

                public double getGrayMean() {
                    return grayMean;
                }

                public void setGrayMean(double grayMean) {
                    this.grayMean = grayMean;
                }

                public double getPitch() {
                    return pitch;
                }

                public void setPitch(double pitch) {
                    this.pitch = pitch;
                }

                public int getVisibleScore() {
                    return visibleScore;
                }

                public void setVisibleScore(int visibleScore) {
                    this.visibleScore = visibleScore;
                }

                public double getYaw() {
                    return yaw;
                }

                public void setYaw(double yaw) {
                    this.yaw = yaw;
                }

                public double getClearityScore() {
                    return clearityScore;
                }

                public void setClearityScore(double clearityScore) {
                    this.clearityScore = clearityScore;
                }
            }

            public static class FaceRectBean {
                /**
                 * width : 0.21306690573692322
                 * x : 0.3645472228527069
                 * y : 0.30906248092651367
                 * height : 0.11936527490615845
                 */

                private double width;
                private double x;
                private double y;
                private double height;

                public double getWidth() {
                    return width;
                }

                public void setWidth(double width) {
                    this.width = width;
                }

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }

                public double getHeight() {
                    return height;
                }

                public void setHeight(double height) {
                    this.height = height;
                }
            }
        }
    }
}
