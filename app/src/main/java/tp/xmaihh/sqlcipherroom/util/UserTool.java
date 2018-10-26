package tp.xmaihh.sqlcipherroom.util;

public class UserTool {
    /**
     * @return 生成的学生姓名。
     * @description 随机生成学生姓名。有20种姓氏；名字长度可能有1-2位，共有6480中组合。共有129600种可能的姓名组合。
     */
    public static String getName() {
        String[] firstNameArray = {
                "李", "王", "张", "刘", "陈",
                "杨", "赵", "黄", "周", "吴",
                "徐", "孙", "胡", "朱", "高",
                "关", "钱", "崔", "孔", "马",
                "欧阳", "太史", "端木", "上官",
                "司马"
        };// 25个姓
        String[] lastNameArray = {
                "伟", "勇", "军", "磊", "涛",
                "斌", "强", "鹏", "杰", "峰",
                "超", "波", "辉", "刚", "健",
                "明", "亮", "俊", "飞", "凯",
                "浩", "华", "平", "鑫", "毅",
                "林", "洋", "宇", "敏", "宁",
                "建", "兵", "旭", "雷", "锋",
                "彬", "龙", "翔", "阳", "剑",
                "静", "敏", "燕", "艳", "丽",
                "娟", "莉", "芳", "萍", "玲",
                "娜", "丹", "洁", "红", "颖",
                "琳", "霞", "婷", "慧", "莹",
                "晶", "华", "倩", "英", "佳",
                "梅", "雪", "蕾", "琴", "璐",
                "伟", "云", "蓉", "青", "薇",
                "欣", "琼", "宁", "平", "媛"
        };// 80个常用于名字的单字
        int firstPos = (int) (25 * Math.random());
        StringBuilder name = new StringBuilder(firstNameArray[firstPos]);
        int lastLen = (int) (2 * Math.random()) + 1;
        /*
         * 为了各函数的统一性，此处也用for循环实现 int lastPos1 = (int) (80 * random()); String lastName =
         * lastNameArray[lastPos1]; if (lastLen == 2) { int lastPos2 = (int) (80 *
         * random()); lastName = lastName + lastNameArray[lastPos2]; }
         */
        for (int i = 0; i < lastLen; i++) {
            int lastPos = (int) (80 * Math.random());
            name.append(lastNameArray[lastPos]);
        }
        return name.toString();
    }

}
