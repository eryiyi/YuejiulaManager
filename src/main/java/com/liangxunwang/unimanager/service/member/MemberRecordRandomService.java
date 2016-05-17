package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.CountDao;
import com.liangxunwang.unimanager.dao.RecordDao;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Server on 2015/7/23.
 */
@Service("memberRecordRandomService")
public class MemberRecordRandomService implements UpdateService{
    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("countDao")
    private CountDao countDao;

    /**
     * 虚拟用户的emp_id和school_id
     * */
    public String[][] school_uuid = {
            {"82d1d353722c4afb80431244803ed728","4012"},
            {"040aa859d788416a90bb124761cf0886","4013"},
            {"58c7568c55904b2095e7af591ae04d84","7001"},
            {"131131ff828b4df99e5b56054574b718","7017"},
            {"54373296e0ac4abeb5e2561580b04f89","7045"},
            {"2f4c6953cff14c19a8ae95635aec3d8a","7067"},
            {"b485e977d94f4735ae941e67c82d80d7","7095"},
            {"af2fba3cbc3848da820692ee588a030a","8001"},
            {"01f7b2dae966497db7b20f0294b4c7cc","8005"},
            {"b39d243c7f4c41049fe300591c9f480f","8008"},
            {"6a6e97d078e74a1a90013bc95237311f","8013"},
            {"0feed25bc19e4cfdb7760b4078926020","8022"},
            {"32c1f8fe372344448f57623c78e483a1","8038"},
            {"7c06e267465f49e9abf4596f33b9e834","8033"},
            {"2588387e7ae440a0967e1fae1a15ee35","8029"},
            {"8cbf6935a2104642a205d1702e9b359b","8027"},
            {"5c5df0ccf0be4bffb8e7006a25c1f513","8050"},
            {"813a9ac0fcd24941aeed386bd76f9c64","8063"},
            {"7a52252d46334ee0a7bb3b83d8c1069a","8083"},
            {"ca5775a3386e4b53a6389ba84dc1370c","8077"},
            {"51c355806f324d18a242f44cc2d34279","8106"},
            {"7ac063d7bda84400a777707cdf52796a","8110"},
            {"6b1e02a5ed95477a9c69cbe7b62c8f25","8107"},
            {"edcec28285da4a79a3aa147e5c228203","8117"},
            {"62849bcfdf54436dae1231b3c643c118","8123"},
            {"4d98139d555843019e25e225270973d4","8120"},
            {"e1d17435bba54f31b90bb36880934b3c","8141"},
            {"062600ace4844ac692b5d0fcc0c012be","8146"},
            {"a5338464d64340b7be422fd64e793bf6","8139"},
            {"a9e41d09088846b1b40a2bd5cff9e143","8141"},
            {"18b8f71686b1403896529fdb8c2746e4","8149"},
            {"4d1a92d626384dc4816fbd15648add46","8151"},
            {"3c80287c8e664278940e60bb02bf1815","8156"},
            {"268897780b78455fa9fafcbf3b47c39b","9007"},
            {"97fbaf403aa84e5b8954face92f1b3cc","8160"},
            {"c88ac423d1924efdba3fca91d020d3f2","9008"},
            {"44679cf6c2c5419086ccab37c9d2de78","12003"},
            {"b8e9a1cf628644a0b273a28b4d16dd46","12001"},
            {"c5531b6bfd7242b3948f00ef3b18174e","12023"},
            {"e2bf07c2befe4f6e82197939a9e8859a","12055"},
            {"b4260262e15742eeb514a009dd2daf20","12053"},
            {"cabfd1fab37b4d4d836a5e090429fea2","12089"},
            {"60a957f5a6264de68ea9807ebebfc3f5","12061"},
            {"a40e50257a8047dfb88134dca872ab43","13022"},
            {"ddd71d246bd044cbb80f9c95358dc726","13001"},
            {"adfd0fb3ac224758ae1d75e5fff27d7d","13085"},
            {"885a8ee278a14f8a9513dbcca45734c6","13079"},
            {"ee0fdd1e822741e39ca46c0b1a178e61","13092"},
            {"f3ba066469384d13b3cb401c44cf3eac","13100"},
            {"9426939cf830479f95a6ee5772c69789","13101"},
            {"c42a657dc40546e59dd4c43883958de5","4012"},
            {"1613de82c214429abdb572b299522d3d","4013"},
            {"d53f0434ff7e42f39d6f9d6b91ad13e7","7001"},
            {"7f6f9a33a7b047f787c25ba03237842c","7017"},
            {"991dcd69f51b4dee8bf97b4263b18a98","7045"},
            {"0b08b1faaec14eb3ab698f490cb80edf","7067"},
            {"2a3191dfa536437e80604877569daa57","7095"},
            {"aba194426a844a9d9f2ff755fc6dc90c","8001"},
            {"69170d04bd10453b838b5d8e9ba5b30b","8005"},
            {"632decdffe264952b53641741b2cd805","8008"},
            {"1b6c3887a5af45d3b56c9beaf0e6d476","8013"},
            {"ecb2fd92ed9240beac2012b7903886ac","8022"},
            {"aeae7e823d3d42559279b26e8dd594df","8038"},
            {"ff685e31141e4796a2f1e3aebce50f4a","8033"},
            {"5bdb3860ec494706a08432bfd5dc8c87","8029"},
            {"420667227c764b599aafc0f406ac2bd3","8027"},
            {"ef5cdb50bbd4441590759a4bc45b3d82","8050"},
            {"50381f8d64464adc864d619745b7f875","8063"},
            {"070b25e9ee734c54b5ea6f72ef931807","8083"},
            {"4055c1efedd646bb8aedbc657956accc","8077"},
            {"dbc1be909ca840d5918d7829e2d13257","8106"},
            {"81666d625fcb4f069329d81dbd934b96","8110"},
            {"28723bb5977942dd812634ef3067b845","8107"},
            {"01275a4ebc9443d2b796946d1fd0af9d","8117"},
            {"93bff53fefbb4ecd90778e559920155c","8123"},
            {"53b8129bea4a4a68ae1981e4ab471a33","8120"},
            {"be9de1093c0349f7b41fd2c17b2b9d0d","9007"},
            {"36778949832f479195b797b628e5f1ba","8141"},
            {"dfe99027e3c142449cf701d22d1ffb69","8146"},
            {"175209d339644b1aa589a7689575b0e4","8139"},
            {"8a71f99b411146359e17f43a91dc4165","8141"},
            {"57589777d9414784b416aedc20ee035c","8149"},
            {"8f97766fdede4931ae0ea063599b4663","8151"},
            {"4121d50ffb6041ffab9a575e9ba842b9","8156"},
            {"125e441c274545ef9cd3f19da9bf9dc0","9007"},
            {"1854ccabf8fc4760bfc87e944cf9acb7","12003"},
            {"b4c3eb35f4dd4a4bba8cda409ad94d70","8160"},
            {"9e88c2b4c62b41a8be1f94339be01bc4","9008"},
            {"44b6010504c24a0abc1b5aa57bc6b6db","12001"},
            {"8c94f0a688674b07be8c62bd502e025f","12023"},
            {"c6c98af677da4a329d9d20d34d5a1d6a","12055"},
            {"bf4e08949d74464ea9c43ecf6023c784","12053"},
            {"2960afe074c047f68bffa5f5aec57a20","12089"},
            {"fc63510e4b45425e802040b4630e91e1","12061"},
            {"52a834a3cb5f4b279036e2f21df0431b","13001"},
            {"0e215db25376432a84008fded385bab5","13022"},
            {"fde3c7fc91aa4535bc9416cf2c464f23","13092"},
            {"3f7df9ff4c4744b78d6858227dd63d91","13079"},
            {"c2e61e26a2c540ac86890d593e410ad8","13100"},
            {"a3f13d87bab04c6cb5469bc13730ce37","13085"},
            {"187cf14cc41748e9a033a608b5fcf81b","13101"}
            };

    @Override
    public Object update(Object object) {
        Record record ;
        //获得采集的文件名
        String[] arrayTitle = StringUtil.getFile(Constants.FILE_PATH);
        if(arrayTitle != null && arrayTitle.length > 0){
            for(int i=0; i<arrayTitle.length ; i++){
                record = new Record();
                record.setRecordType("0");
                if (arrayTitle[i] != null && arrayTitle[i].length() > 0){
                    if(arrayTitle[i].endsWith(".txt")){
                        record.setRecordCont(arrayTitle[i].replaceAll(".txt", ""));
                    }else {
                        record.setRecordCont(arrayTitle[i]);
                    }
                }else {
                    continue;
                }
                int numtmp = new Random().nextInt(100);
                record.setRecordEmpId(String.valueOf(school_uuid[numtmp][0]));
                record.setRecordSchoolId(String.valueOf(school_uuid[numtmp][1]));

                record.setRecordId(UUIDFactory.random());
                record.setDateLine(System.currentTimeMillis() + "");
                record.setRecordIsDel("0");
                record.setRecordIsUse("0");
                recordDao.save(record);
                //更新积分分数
                countDao.update(record.getRecordEmpId(), record.getRecordType());
            }
        }
        //删除文件夹下所有文件
        try {
            StringUtil.deletefile(Constants.FILE_PATH);
        }catch (Exception e){
            e.printStackTrace();
        }

         return null;
    }
}
