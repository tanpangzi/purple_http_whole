package com.zimi.zimixing.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 省份信息bean
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class ProvinceBean extends BaseBean {

    /**
     * id : 35
     * name : 河北省
     * city : [{"id":"36","name":"石家庄市","city":[{"id":"37","name":"长安区"},{"id":"38","name":"桥东区"},{"id":"39","name":"桥西区"},{"id":"40","name":"新华区"},{"id":"41","name":"井陉矿区"},{"id":"42","name":"裕华区"},{"id":"43","name":"井陉县"},{"id":"44","name":"正定县"},{"id":"45","name":"栾城县"},{"id":"46","name":"行唐县"},{"id":"47","name":"灵寿县"},{"id":"48","name":"高邑县"},{"id":"49","name":"深泽县"},{"id":"50","name":"赞皇县"},{"id":"51","name":"无极县"},{"id":"52","name":"平山县"},{"id":"53","name":"元氏县"},{"id":"54","name":"赵县"},{"id":"55","name":"辛集市"},{"id":"56","name":"藁城市"},{"id":"57","name":"晋州市"},{"id":"58","name":"新乐市"},{"id":"59","name":"鹿泉市"}]},{"id":"60","name":"唐山市","city":[{"id":"61","name":"路南区"},{"id":"62","name":"路北区"},{"id":"63","name":"古冶区"},{"id":"64","name":"开平区"},{"id":"65","name":"丰南区"},{"id":"66","name":"丰润区"},{"id":"67","name":"曹妃甸区"},{"id":"68","name":"滦县"},{"id":"69","name":"滦南县"},{"id":"70","name":"乐亭县"},{"id":"71","name":"迁西县"},{"id":"72","name":"玉田县"},{"id":"73","name":"遵化市"},{"id":"74","name":"迁安市"}]},{"id":"75","name":"秦皇岛市","city":[{"id":"76","name":"海港区"},{"id":"77","name":"山海关区"},{"id":"78","name":"北戴河区"},{"id":"79","name":"青龙满族自治县"},{"id":"80","name":"昌黎县"},{"id":"81","name":"抚宁县"},{"id":"82","name":"卢龙县"}]},{"id":"83","name":"邯郸市","city":[{"id":"84","name":"邯山区"},{"id":"85","name":"丛台区"},{"id":"86","name":"复兴区"},{"id":"87","name":"峰峰矿区"},{"id":"88","name":"邯郸县"},{"id":"89","name":"临漳县"},{"id":"90","name":"成安县"},{"id":"91","name":"大名县"},{"id":"92","name":"涉县"},{"id":"93","name":"磁县"},{"id":"94","name":"肥乡县"},{"id":"95","name":"永年县"},{"id":"96","name":"邱县"},{"id":"97","name":"鸡泽县"},{"id":"98","name":"广平县"},{"id":"99","name":"馆陶县"},{"id":"100","name":"魏县"},{"id":"101","name":"曲周县"},{"id":"102","name":"武安市"}]},{"id":"103","name":"邢台市","city":[{"id":"104","name":"桥东区"},{"id":"105","name":"桥西区"},{"id":"106","name":"邢台县"},{"id":"107","name":"临城县"},{"id":"108","name":"内丘县"},{"id":"109","name":"柏乡县"},{"id":"110","name":"隆尧县"},{"id":"111","name":"任县"},{"id":"112","name":"南和县"},{"id":"113","name":"宁晋县"},{"id":"114","name":"巨鹿县"},{"id":"115","name":"新河县"},{"id":"116","name":"广宗县"},{"id":"117","name":"平乡县"},{"id":"118","name":"威县"},{"id":"119","name":"清河县"},{"id":"120","name":"临西县"},{"id":"121","name":"南宫市"},{"id":"122","name":"沙河市"}]},{"id":"123","name":"保定市","city":[{"id":"124","name":"新市区"},{"id":"125","name":"北市区"},{"id":"126","name":"南市区"},{"id":"127","name":"满城县"},{"id":"128","name":"清苑县"},{"id":"129","name":"涞水县"},{"id":"130","name":"阜平县"},{"id":"131","name":"徐水县"},{"id":"132","name":"定兴县"},{"id":"133","name":"唐县"},{"id":"134","name":"高阳县"},{"id":"135","name":"容城县"},{"id":"136","name":"涞源县"},{"id":"137","name":"望都县"},{"id":"138","name":"安新县"},{"id":"139","name":"易县"},{"id":"140","name":"曲阳县"},{"id":"141","name":"蠡县"},{"id":"142","name":"顺平县"},{"id":"143","name":"博野县"},{"id":"144","name":"雄县"},{"id":"145","name":"涿州市"},{"id":"146","name":"定州市"},{"id":"147","name":"安国市"},{"id":"148","name":"高碑店市"}]},{"id":"149","name":"张家口市","city":[{"id":"150","name":"桥东区"},{"id":"151","name":"桥西区"},{"id":"152","name":"宣化区"},{"id":"153","name":"下花园区"},{"id":"154","name":"宣化县"},{"id":"155","name":"张北县"},{"id":"156","name":"康保县"},{"id":"157","name":"沽源县"},{"id":"158","name":"尚义县"},{"id":"159","name":"蔚县"},{"id":"160","name":"阳原县"},{"id":"161","name":"怀安县"},{"id":"162","name":"万全县"},{"id":"163","name":"怀来县"},{"id":"164","name":"涿鹿县"},{"id":"165","name":"赤城县"},{"id":"166","name":"崇礼县"}]},{"id":"167","name":"承德市","city":[{"id":"168","name":"双桥区"},{"id":"169","name":"双滦区"},{"id":"170","name":"鹰手营子矿区"},{"id":"171","name":"承德县"},{"id":"172","name":"兴隆县"},{"id":"173","name":"平泉县"},{"id":"174","name":"滦平县"},{"id":"175","name":"隆化县"},{"id":"176","name":"丰宁满族自治县"},{"id":"177","name":"宽城满族自治县"},{"id":"178","name":"围场满族蒙古族自治县"}]},{"id":"179","name":"沧州市","city":[{"id":"180","name":"新华区"},{"id":"181","name":"运河区"},{"id":"182","name":"沧县"},{"id":"183","name":"青县"},{"id":"184","name":"东光县"},{"id":"185","name":"海兴县"},{"id":"186","name":"盐山县"},{"id":"187","name":"肃宁县"},{"id":"188","name":"南皮县"},{"id":"189","name":"吴桥县"},{"id":"190","name":"献县"},{"id":"191","name":"孟村回族自治县"},{"id":"192","name":"泊头市"},{"id":"193","name":"任丘市"},{"id":"194","name":"黄骅市"},{"id":"195","name":"河间市"}]},{"id":"196","name":"廊坊市","city":[{"id":"197","name":"安次区"},{"id":"198","name":"广阳区"},{"id":"199","name":"固安县"},{"id":"200","name":"永清县"},{"id":"201","name":"香河县"},{"id":"202","name":"大城县"},{"id":"203","name":"文安县"},{"id":"204","name":"大厂回族自治县"},{"id":"205","name":"霸州市"},{"id":"206","name":"三河市"}]},{"id":"207","name":"衡水市","city":[{"id":"208","name":"桃城区"},{"id":"209","name":"枣强县"},{"id":"210","name":"武邑县"},{"id":"211","name":"武强县"},{"id":"212","name":"饶阳县"},{"id":"213","name":"安平县"},{"id":"214","name":"故城县"},{"id":"215","name":"景县"},{"id":"216","name":"阜城县"},{"id":"217","name":"冀州市"},{"id":"218","name":"深州市"}]}]
     */
    /** 省份信息 */
    private String name = "";
    /** 省份ID */
    private String id = "";
    /** 城市列表 */
    private ArrayList<CityBean> cityList = new ArrayList<>();


    @Override
    protected void init(JSONObject jSon) throws JSONException {

        name = jSon.optString("name", "");
        id = jSon.optString("id", "");

        cityList = (ArrayList<CityBean>) BaseBean.toModelList(jSon.optString("city", ""), CityBean.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CityBean> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityBean> cityList) {
        this.cityList = cityList;
    }
}
