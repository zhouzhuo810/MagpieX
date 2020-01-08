package me.zhouzhuo810.magpiexdemo.api.entity;

import java.util.List;

/**
 * Created by zz on 2017/6/9.
 */

public class MainInfoBean {

    /**
     * code : 1
     * msg : ok
     * data : {"comInfo":{"name":"宁波华翔全场报警系统","logo":"/image/logo.png"},"reasons":[{"name":"设备问题","color":"#b61648"},{"name":"模具问题","color":"#920683"},{"name":"工艺问题","color":"#5f1884"},{"name":"物流外购","color":"#8ec51f"},{"name":"质量问题","color":"#1a1f8a"},{"name":"物流成品","color":"#fef000"},{"name":"物流包材","color":"#009a43"},{"name":"物流原料","color":"#0a6072"}],"errorList":[{"recordId":"481","audioPath":"E:/MyWork/临时/fabu/bgm/481工艺问题.wav","reasonName":"工艺问题","content":"L538 饰柱出现工艺问题"},{"recordId":"484","audioPath":"E:/MyWork/临时/fabu/bgm/484物流外购.wav","reasonName":"物流外购","content":"L538 饰柱出现物流外购"}],"designWidth":"1920","designHeight":"700","map":[{"leftTop":"1360,548","rightBottom":"1520,700","title":"","macName":"1#号成型机","color":[]},{"leftTop":"1200,548","rightBottom":"1360,700","title":"","macName":"2#号成型机","color":[]},{"leftTop":"1040,548","rightBottom":"1114,700","title":"","macName":"3#号成型机","color":[]},{"leftTop":"880,548","rightBottom":"1040,700","title":"","macName":"4#号成型机","color":[]},{"leftTop":"720,548","rightBottom":"880,700","title":"","macName":"5#号成型机","color":[]},{"leftTop":"560,548","rightBottom":"720,700","title":"","macName":"6#号成型机","color":[]},{"leftTop":"400,548","rightBottom":"560,700","title":"","macName":"7#号成型机","color":[]},{"leftTop":"80,434","rightBottom":"240,548","title":"","macName":"10#号成型机","color":[]},{"leftTop":"240,434","rightBottom":"400,548","title":"","macName":"11#号成型机","color":[]},{"leftTop":"400,434","rightBottom":"560,548","title":"","macName":"12#号成型机","color":[]},{"leftTop":"560,434","rightBottom":"720,548","title":"","macName":"13#号成型机","color":[]},{"leftTop":"720,434","rightBottom":"880,548","title":"","macName":"14#号成型机","color":[]},{"leftTop":"880,434","rightBottom":"1040,548","title":"","macName":"15#号成型机","color":[]},{"leftTop":"1040,434","rightBottom":"1200,548","title":"","macName":"16#号成型机","color":[]},{"leftTop":"1200,434","rightBottom":"1360,548","title":"","macName":"17#号成型机","color":[]},{"leftTop":"1360,434","rightBottom":"1520,548","title":"","macName":"18#号成型机","color":[]},{"leftTop":"1520,434","rightBottom":"1680,548","title":"","macName":"19#号成型机","color":[]},{"leftTop":"1680,434","rightBottom":"1840,548","title":"","macName":"20#号成型机","color":[]},{"leftTop":"80,548","rightBottom":"161,700","title":"","macName":"土狼A柱/NCV2 A柱","color":[]},{"leftTop":"161,548","rightBottom":"240,700","title":"","macName":"MK内盖板/AP11排水槽","color":[]},{"leftTop":"1114,548","rightBottom":"1200,624","title":"","macName":"A+A柱装配","color":[]},{"leftTop":"1598,548","rightBottom":"1758,700","title":"","macName":"L538侧围焊接机","color":[]},{"leftTop":"1114,624","rightBottom":"1200,700","title":"","macName":"L538 饰柱","color":[{"color":"#5f1884","name":"工艺问题","MachineID":"229"},{"color":"#8ec51f","name":"物流外购","MachineID":"229"}]},{"leftTop":"641,228","rightBottom":"719,410","title":"","macName":"A+右前门焊接机","color":[]},{"leftTop":"719,228","rightBottom":"801,410","title":"","macName":"A+右后门焊接机","color":[]},{"leftTop":"801,228","rightBottom":"880,410","title":"","macName":"A+左后门焊接机","color":[]},{"leftTop":"880,228","rightBottom":"959,410","title":"","macName":"A+左前门焊接机","color":[]},{"leftTop":"1281,228","rightBottom":"1358,410","title":"","macName":"MK左前门焊接机","color":[]},{"leftTop":"1358,228","rightBottom":"1435,410","title":"","macName":"MK左后门焊接机","color":[]},{"leftTop":"1119,228","rightBottom":"1199,410","title":"","macName":"MK右前门焊接机","color":[]},{"leftTop":"1199,228","rightBottom":"1281,410","title":"","macName":"MK右后门焊接机","color":[]},{"leftTop":"1833,228","rightBottom":"1920,410","title":"","macName":"MQB左前焊接机","color":[]},{"leftTop":"1755,228","rightBottom":"1833,410","title":"","macName":"MQB右前焊接机","color":[]},{"leftTop":"1677,228","rightBottom":"1755,410","title":"","macName":"MQB左后焊接机","color":[]},{"leftTop":"1595,228","rightBottom":"1677,410","title":"","macName":"MQB右后焊接机","color":[]},{"leftTop":"1677,0","rightBottom":"1919,142","title":"","macName":"Fabia门板焊接机","color":[]},{"leftTop":"1595,0","rightBottom":"1677,142","title":"","macName":"NCV3门板焊接机","color":[]},{"leftTop":"240,548","rightBottom":"400,700","title":"","macName":"VW511 饰柱焊接机","color":[]},{"leftTop":"401,94","rightBottom":"721,142","title":"","macName":"VS20后侧围焊接机","color":[]},{"leftTop":"401,44","rightBottom":"722,94","title":"","macName":"VS20中门焊接机","color":[]},{"leftTop":"401,0","rightBottom":"721,44","title":"","macName":"VS20前门焊接机","color":[]},{"leftTop":"322,228","rightBottom":"401,410","title":"","macName":"Tiguan-NF左前焊接机","color":[]},{"leftTop":"243,228","rightBottom":"322,410","title":"","macName":"Tiguan-NF右前焊接机","color":[]},{"leftTop":"161,228","rightBottom":"243,410","title":"","macName":"Tiguan-NF左后焊接机","color":[]},{"leftTop":"83,228","rightBottom":"161,410","title":"","macName":"Tiguan-NF右后焊接机","color":[]},{"leftTop":"1517,0","rightBottom":"1595,142","title":"","macName":"Fabia扶手包覆","color":[]},{"leftTop":"0,0","rightBottom":"242,142","title":"","macName":"VS20扶手包覆","color":[]},{"leftTop":"1281,0","rightBottom":"1435,142","title":"","macName":"Tiguan-NF/VW511低音炮焊接机","color":[]},{"leftTop":"881,0","rightBottom":"1199,142","title":"","macName":"3con覆皮房","color":[]},{"leftTop":"","rightBottom":"","title":"","macName":"裁布房","color":[]},{"leftTop":"","rightBottom":"","title":"","macName":"GP12返工线","color":[]},{"leftTop":"83,162","rightBottom":"401,228","title":"Tiguan NF assembly","macName":"","color":[]},{"leftTop":"641,162","rightBottom":"959,228","title":" A+ assembly","macName":"","color":[]},{"leftTop":"1119,162","rightBottom":"1435,228","title":" MK assembly","macName":"","color":[]},{"leftTop":"1595,162","rightBottom":"1920,228","title":"MQB assembly","macName":"","color":[]}]}
     */

    private String code;
    private String msg;
    private DataEntity data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * comInfo : {"name":"宁波华翔全场报警系统","logo":"/image/logo.png"}
         * reasons : [{"name":"设备问题","color":"#b61648"},{"name":"模具问题","color":"#920683"},{"name":"工艺问题","color":"#5f1884"},{"name":"物流外购","color":"#8ec51f"},{"name":"质量问题","color":"#1a1f8a"},{"name":"物流成品","color":"#fef000"},{"name":"物流包材","color":"#009a43"},{"name":"物流原料","color":"#0a6072"}]
         * errorList : [{"recordId":"481","audioPath":"E:/MyWork/临时/fabu/bgm/481工艺问题.wav","reasonName":"工艺问题","content":"L538 饰柱出现工艺问题"},{"recordId":"484","audioPath":"E:/MyWork/临时/fabu/bgm/484物流外购.wav","reasonName":"物流外购","content":"L538 饰柱出现物流外购"}]
         * designWidth : 1920
         * designHeight : 700
         * map : [{"leftTop":"1360,548","rightBottom":"1520,700","title":"","macName":"1#号成型机","color":[]},{"leftTop":"1200,548","rightBottom":"1360,700","title":"","macName":"2#号成型机","color":[]},{"leftTop":"1040,548","rightBottom":"1114,700","title":"","macName":"3#号成型机","color":[]},{"leftTop":"880,548","rightBottom":"1040,700","title":"","macName":"4#号成型机","color":[]},{"leftTop":"720,548","rightBottom":"880,700","title":"","macName":"5#号成型机","color":[]},{"leftTop":"560,548","rightBottom":"720,700","title":"","macName":"6#号成型机","color":[]},{"leftTop":"400,548","rightBottom":"560,700","title":"","macName":"7#号成型机","color":[]},{"leftTop":"80,434","rightBottom":"240,548","title":"","macName":"10#号成型机","color":[]},{"leftTop":"240,434","rightBottom":"400,548","title":"","macName":"11#号成型机","color":[]},{"leftTop":"400,434","rightBottom":"560,548","title":"","macName":"12#号成型机","color":[]},{"leftTop":"560,434","rightBottom":"720,548","title":"","macName":"13#号成型机","color":[]},{"leftTop":"720,434","rightBottom":"880,548","title":"","macName":"14#号成型机","color":[]},{"leftTop":"880,434","rightBottom":"1040,548","title":"","macName":"15#号成型机","color":[]},{"leftTop":"1040,434","rightBottom":"1200,548","title":"","macName":"16#号成型机","color":[]},{"leftTop":"1200,434","rightBottom":"1360,548","title":"","macName":"17#号成型机","color":[]},{"leftTop":"1360,434","rightBottom":"1520,548","title":"","macName":"18#号成型机","color":[]},{"leftTop":"1520,434","rightBottom":"1680,548","title":"","macName":"19#号成型机","color":[]},{"leftTop":"1680,434","rightBottom":"1840,548","title":"","macName":"20#号成型机","color":[]},{"leftTop":"80,548","rightBottom":"161,700","title":"","macName":"土狼A柱/NCV2 A柱","color":[]},{"leftTop":"161,548","rightBottom":"240,700","title":"","macName":"MK内盖板/AP11排水槽","color":[]},{"leftTop":"1114,548","rightBottom":"1200,624","title":"","macName":"A+A柱装配","color":[]},{"leftTop":"1598,548","rightBottom":"1758,700","title":"","macName":"L538侧围焊接机","color":[]},{"leftTop":"1114,624","rightBottom":"1200,700","title":"","macName":"L538 饰柱","color":[{"color":"#5f1884","name":"工艺问题","MachineID":"229"},{"color":"#8ec51f","name":"物流外购","MachineID":"229"}]},{"leftTop":"641,228","rightBottom":"719,410","title":"","macName":"A+右前门焊接机","color":[]},{"leftTop":"719,228","rightBottom":"801,410","title":"","macName":"A+右后门焊接机","color":[]},{"leftTop":"801,228","rightBottom":"880,410","title":"","macName":"A+左后门焊接机","color":[]},{"leftTop":"880,228","rightBottom":"959,410","title":"","macName":"A+左前门焊接机","color":[]},{"leftTop":"1281,228","rightBottom":"1358,410","title":"","macName":"MK左前门焊接机","color":[]},{"leftTop":"1358,228","rightBottom":"1435,410","title":"","macName":"MK左后门焊接机","color":[]},{"leftTop":"1119,228","rightBottom":"1199,410","title":"","macName":"MK右前门焊接机","color":[]},{"leftTop":"1199,228","rightBottom":"1281,410","title":"","macName":"MK右后门焊接机","color":[]},{"leftTop":"1833,228","rightBottom":"1920,410","title":"","macName":"MQB左前焊接机","color":[]},{"leftTop":"1755,228","rightBottom":"1833,410","title":"","macName":"MQB右前焊接机","color":[]},{"leftTop":"1677,228","rightBottom":"1755,410","title":"","macName":"MQB左后焊接机","color":[]},{"leftTop":"1595,228","rightBottom":"1677,410","title":"","macName":"MQB右后焊接机","color":[]},{"leftTop":"1677,0","rightBottom":"1919,142","title":"","macName":"Fabia门板焊接机","color":[]},{"leftTop":"1595,0","rightBottom":"1677,142","title":"","macName":"NCV3门板焊接机","color":[]},{"leftTop":"240,548","rightBottom":"400,700","title":"","macName":"VW511 饰柱焊接机","color":[]},{"leftTop":"401,94","rightBottom":"721,142","title":"","macName":"VS20后侧围焊接机","color":[]},{"leftTop":"401,44","rightBottom":"722,94","title":"","macName":"VS20中门焊接机","color":[]},{"leftTop":"401,0","rightBottom":"721,44","title":"","macName":"VS20前门焊接机","color":[]},{"leftTop":"322,228","rightBottom":"401,410","title":"","macName":"Tiguan-NF左前焊接机","color":[]},{"leftTop":"243,228","rightBottom":"322,410","title":"","macName":"Tiguan-NF右前焊接机","color":[]},{"leftTop":"161,228","rightBottom":"243,410","title":"","macName":"Tiguan-NF左后焊接机","color":[]},{"leftTop":"83,228","rightBottom":"161,410","title":"","macName":"Tiguan-NF右后焊接机","color":[]},{"leftTop":"1517,0","rightBottom":"1595,142","title":"","macName":"Fabia扶手包覆","color":[]},{"leftTop":"0,0","rightBottom":"242,142","title":"","macName":"VS20扶手包覆","color":[]},{"leftTop":"1281,0","rightBottom":"1435,142","title":"","macName":"Tiguan-NF/VW511低音炮焊接机","color":[]},{"leftTop":"881,0","rightBottom":"1199,142","title":"","macName":"3con覆皮房","color":[]},{"leftTop":"","rightBottom":"","title":"","macName":"裁布房","color":[]},{"leftTop":"","rightBottom":"","title":"","macName":"GP12返工线","color":[]},{"leftTop":"83,162","rightBottom":"401,228","title":"Tiguan NF assembly","macName":"","color":[]},{"leftTop":"641,162","rightBottom":"959,228","title":" A+ assembly","macName":"","color":[]},{"leftTop":"1119,162","rightBottom":"1435,228","title":" MK assembly","macName":"","color":[]},{"leftTop":"1595,162","rightBottom":"1920,228","title":"MQB assembly","macName":"","color":[]}]
         */

        private ComInfoEntity comInfo;
        private int designWidth;
        private int designHeight;
        private List<MapEntity> map;

        public ComInfoEntity getComInfo() {
            return comInfo;
        }

        public void setComInfo(ComInfoEntity comInfo) {
            this.comInfo = comInfo;
        }

        public int getDesignWidth() {
            return designWidth;
        }

        public void setDesignWidth(int designWidth) {
            this.designWidth = designWidth;
        }

        public int getDesignHeight() {
            return designHeight;
        }

        public void setDesignHeight(int designHeight) {
            this.designHeight = designHeight;
        }

        public List<MapEntity> getMap() {
            return map;
        }

        public void setMap(List<MapEntity> map) {
            this.map = map;
        }

        public static class ComInfoEntity {
            /**
             * name : 宁波华翔全场报警系统
             * logo : /image/logo.png
             */

            private String name;
            private String logo;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

        public static class MapEntity {
            /**
             * leftTop : 1360,548
             * rightBottom : 1520,700
             * title :
             * macName : 1#号成型机
             * color : []
             */
            private String leftTop;
            private String rightBottom;
            private String title;
            private String macName;
            private int textSize;
            private String rectColor;
            private List<ColorEntity> color;

            public String getRectColor() {
                return rectColor;
            }

            public void setRectColor(String rectColor) {
                this.rectColor = rectColor;
            }

            public String getLeftTop() {
                return leftTop;
            }

            public void setLeftTop(String leftTop) {
                this.leftTop = leftTop;
            }

            public String getRightBottom() {
                return rightBottom;
            }

            public void setRightBottom(String rightBottom) {
                this.rightBottom = rightBottom;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMacName() {
                return macName;
            }

            public void setMacName(String macName) {
                this.macName = macName;
            }

            public int getTextSize() {
                return textSize;
            }

            public void setTextSize(int textSize) {
                this.textSize = textSize;
            }

            public List<ColorEntity> getColor() {
                return color;
            }

            public void setColor(List<ColorEntity> color) {
                this.color = color;
            }

            public static class ColorEntity {
                /**
                 * value : 颜色值，如#2f2f2f
                 * name : 故障原因,如设备问题
                 */

                private String value;
                private String name;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
