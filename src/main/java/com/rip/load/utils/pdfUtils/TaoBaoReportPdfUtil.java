package com.rip.load.utils.pdfUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 生成淘宝报告
 * @Author: FYH
 * @Date: Created in 13:56 2019/5/21
 * @Modified:
 */
public class TaoBaoReportPdfUtil {

    /**
     * 淘宝报告
     * @throws IOException
     * @throws DocumentException
     */
    public static Boolean taoBaoReport(String json,String savePath) {
        try {
            //################################################获取数据############################################################
            Map<String, Object> jsonMap = JSON.parseObject(json);
            if (jsonMap.get("code") != null && Integer.valueOf(jsonMap.get("code").toString()) == 200) {
                Map<String, Object> resultMap = JSON.parseObject(jsonMap.get("result").toString());
                Map<String, Object> userCustomerMap = JSON.parseObject(resultMap.get("userCustomer").toString());
                JSONArray itemListArray = JSONArray.parseArray(resultMap.get("itemList").toString());//用户详情
                for (Object items : itemListArray) {
                    Map<String, Object> itemsMap = JSON.parseObject(String.valueOf(items));
                    if ("15".equals(String.valueOf(itemsMap.get("type")))) {//淘宝报告

                        // 在指定目录下创建一个文件
                        File file = new File(savePath);
                        file.createNewFile();
                        // 建立一个Document对象
                        Document document = new Document();
                        // 设置页面大小
                        document.setPageSize(PageSize.A4);
                        // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
                        PdfWriter.getInstance(document, new FileOutputStream(file));
                        document.open();

                        //从json中取出数据
                        Map<String,Object> resultJsonMap = JSON.parseObject(String.valueOf(itemsMap.get("resultJson")));
                        Map<String,Object> dataMap = JSON.parseObject(resultJsonMap.get("data").toString());
                        Map<String,Object> basicInfoMap = JSON.parseObject(dataMap.get("basicInfo").toString());//用户基本信息
                        Map<String,Object> alipayInfoMap = JSON.parseObject(dataMap.get("alipayInfo").toString());//绑定的支付宝信息
                        JSONArray addressesArray = JSON.parseArray(dataMap.get("addresses").toString());//收货地址
                        JSONArray orderDetailsArray = JSON.parseArray(dataMap.get("orderDetails").toString());//订单信息，商品信息、商家信息

                        PdfPTable table = CreateTableUtil.createTable(1);
                        Image img = null;
                        try{
                            img = Image.getInstance("../img/taobao.jpg");//图片的地址
                        }catch (Exception e){
                        }
                        if(img == null)
                            img = Image.getInstance("/usr/local/tomcatfangdi-8088/webapps/img/taobao.jpg");//图片的地址

                        img.scaleAbsolute(CreateTableUtil.mmTopx(200),CreateTableUtil.mmTopx(297));
                        PdfPCell photoCell = new PdfPCell(img);
                        photoCell.setBorder(0);
                        photoCell.setHorizontalAlignment(1);
                        table.addCell(photoCell);
                        document.add(table);

                        //################################################授权协议############################################################
                        Paragraph paragraph = new Paragraph("个人信息查询授权协议",CreateTableUtil.headfontfirst);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));
                        paragraph = new Paragraph("重要提示：为保护用户信息隐私，本协议在数据展示时已经对被查询人的信息展示做了脱敏处理，但是不影响本授权查询协议的有效性。为保障您的权益请您务必审慎阅读、理解并同意签署本协议，本协议的授权验证码将在人脸识别后通过短信方式发送。\n\n", CreateTableUtil.AgreementBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("本协议是您（下称“用户”、“您”）与睿普征信服务（苏州）有限公司（下称睿普征信”、“我们”）之间就“个人信息授权”相关事宜（下称“本服务）所订立的协议。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("本协议包含了睿普征信采集、存储、保护和使用您的个人信息的条款，我们建议您完整地阅读本协议条款，特别是以下划线、加粗等方式标注与您的权益存在或可能存在重大关系的部分。除非您接受本协议所有条款，否则请不要使用本服务。签名即表示您已同意我们按照本协议来合法使用和保护您的个人信息。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("为充分保障您个人信息的安全性，睿普征信在此特别声明：\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1、鉴于您须授权商业伙伴并由该商业伙伴告知本服务后方能进入并使用本服务，自您使用本服务时起即视为您与商业伙伴之间已存在合法的、充分的、必要的、不可撤销的授权。即您已明确授权商业伙伴为验证您的真实身份及评估您的信用信息而对您的身份信息及相关信用信息进行获取、验证、存储并在约定范围内使用；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2、睿普征信仅是您授权采集和使用您个人信息的技术服务提供方。您在此明确授权并同意，由睿普征信对您的信息进行加工、储存、整理并提供给商业伙伴在约定范围内使用。睿普征信会采取合理、必要措施以保护您的隐私和信息安全；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("3、未经您的授权，睿普征信不会将您的信息提供给任何其他第三方；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("4、睿普征信不会保存您的账户密码信息，睿普征信仅会在您每次单独授权的情况下，方予采集相应信息，睿普征信绝不会主动自行采集您的任何信息。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第一章 数据采集服务\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第一条    睿普征信无法确保您授权采集的个人信息源数据的准确性、数据采集通道是否持续通畅，因此睿普征信无法保证本服务提供的持续性、及时性、准确性，如您发现经睿普征信采集的个人信息数据提供给经您授权的商业伙伴存在不准确或错误的情况，可联系您信息所在平台进行更正或删除。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第二条    您在此充分地、有效地、不可撤销地明示同意并授权睿普征信采集以下个人信息：\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1. 用户注册睿普征信账号时，用户根据睿普征信要求提供个人注册信息，包括但不限于姓名、性别、身份证号码、住址、联系方式等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2. 在用户使用睿普征信产品服务，或访问睿普征信产品时，睿普征信自动接收并记录的用户的浏览器和设备上的信息，包括但不限于用户的宽带类型、IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息等数据；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("3. 用户使用睿普征信产品时进行授权登录、认证账单等操作所提供的通讯运营商、邮箱、网银、电商等相关个人信息，具体如下：\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("a）通讯运营商信息：登记姓名、手机号码、身份证号码（如有）、套餐信息、账单信息、通话记录等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("b）邮箱账单信息：帐号信息、发件人、主题，根据关键字在用户“收件箱”、“已发送”、“群邮件”内的邮件进行匹配，包括银行电子账单的相关信息等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("c）电商平台信息：实名认证状态、本人收货人姓名、本人收货人固话/手机、本人收货人地址）、权益信息（花呗额度、白条额度等）；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("d）支付宝信息：绑定手机号（前3后4位）、实名认证状态、资产信息、交易记录、逾期还款记录；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("e）公积金信息：缴纳公积金单位名称、账户余额、缴纳基数、缴纳记录、是否有贷款及贷款相关信息等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("f）社保信息：缴纳单位、缴纳基数、缴纳记录、参保险种信息等； \n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("g）车险保单信息：车辆投保人姓名、身份证，车辆投保信息、车辆基本信息（车牌号、车辆型号、出厂日期、发动机号、车架号等）、车辆出险记录（部分）等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("h）QQ信息：用户QQ注册信息、昵称；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("i）学历学籍信息：学信网用户注册信息、姓名、身份证号码、学历学籍信息（专业、入学/毕业时间、证书照片等）等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("j）个人征信信息：央行个人信用中心用户姓名、身份证（掩码）、银行卡数量、信用卡数量、信用卡额度、额度使用信息、还款逾期信息、个人信用被查询记录、房贷信息等；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("k）本地通讯录信息：包括姓名、电话号码；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("l）其他经用户明确授权的相关信息；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("4. 在用户使用睿普征信提供的相关业务时，用户应确认已对商业伙伴（查询主体必须非个人用户，本协议统称为商业伙伴）进行有效的、充分的、不可撤销的授权，授权该等商业伙伴查询、验证用户的相关个人信息并以信用评估为目的进行使用。为实现用户与商业伙伴之间的合作目的，用户及商业伙伴有效地、充分地、不可撤销地授权睿普征信对上述商业伙伴需要查询、验证或评估的用户个人信息进行收集、查询验证、处理、并提供给商业伙伴在约定范围内进行使用。睿普征信将通过用户提供的个人信息向依法设立的征信机构查询用户的相关信用信息，包括但不限于任何信用记录、信用分、信用报告等信息；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("5. 睿普征信不会采集用户的宗教信仰、基因、指纹、血型、疾病和病史信息以及法律、行政法规规定禁止采集的其他个人信息。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第二章 信息使用\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第三条    睿普征信将以高度审慎义务对待用户个人信息，为使用户个人知晓用户数据所经流程，睿普征信特告知如下：睿普征信在授权范围内为实现用户与商业伙伴之间的合同目的，对授权采集的用户信息将进行以下处理与使用：\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1. 将信息进行归类整合和处理（数据清洗、加工、加密与掩码等），以提供给商业伙伴对用户信用情况进行评估，并依此对睿普征信的服务进行改进；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2. 比较信息的准确性并与第三方进行验证，例如，将用户向睿普征信提交的身份信息与身份验证的服务机构进行验证；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("3. 经用户明确同意并授权的其他用途；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("4. 除本协议明确阐述和相关法律规定外，睿普征信不会向任何无关第三方提供、披露或交易用户的个人信息。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第三章 除外责任\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第四条    因您的过错导致的任何损失，该过错包括但不限于:操作不当、遗忘或泄露密码、密码被他人破解、您使用的计算机系统被第三方侵入、用户委托他人代理使用本服务时他人恶意或不当操作而造成的损失，由您自行负责。除非睿普征信单方故意或重大过失直接导致您遭受损失外，睿普征信不承担责任。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第五条    使用本服务可能会由于采集数据源系统采取的一些安全管控措施导致您在重新身份验证或采集数据源系统要求的其他操作之前，无法正常登录采集数据源系统或相关系统，以及其提供的服务。您充分知晓并接受上述风险及因此对您可能造成的影响，除法律有明确规定外，睿普征信无须承担责任。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第四章 账户安全及管理\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第六条    您了解并同意，确保基于使用本服务所需账户及密码的机密安全对您非常重要，您应妥善保管这些机密信息。您将对利用该账户及密码所进行的一切行动及言论，负完全的责任，并同意以下事项：\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1、您不对其他任何人泄露账户或密码，亦不可使用其他任何人的账户或密码。因黑客、病毒或您的保管疏忽等非睿普征信原因导致您的账户遭他人非法使用的，睿普征信不承担责任；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2、冒用他人账户及密码的，睿普征信及第三方机构保留追究实际使用人连带责任的权利。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第七条    如发现有第三人冒用或盗用您的账户及密码，或其他任何未经您合法授权而使用的情形，您应立即以有效方式通知睿普征信，且马上修改密码，要求睿普征信暂停相关服务。同时，您理解睿普征信对您的请求采取行动需要合理期限，在此之前，睿普征信对第三人使用该服务所导致的损失不承担责任。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第八条    睿普征信有权基于单方独立判断，在认为可能不利于睿普征信提供服务或涉及违法违规等情形时，可不经通知而先行暂停、中断或终止向您提供本协议项下的全部或部分服务，且无需因此对您或任何第三方承担任何责任。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("前述情形包括但不限于：\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1、睿普征信认为您不是采集信息的信息权利人时；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2、睿普征信发现异常情况或有合理理由怀疑操作有风睑或有违法之虞时；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("4、睿普征信认为您已经违反本协议中规定的各类规则；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("5、睿普征信基于上述原因之外，根据其单独判断需先行暂停、中断或终止向您提供本协议项下的全部或部分用户服务。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第五章 服务中断或故障\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第九条    您同意，因下列原因导致睿普征信及第三方机构无法正常提供服务的，睿普征信及第三方机构不承担责任；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1、睿普征信及第三方机构等承载本服务系统停机维护期间；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2、您的电脑，手机软件和通信线路、供电线路出现故障的；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("3、您操作不当或通过非睿普征信及第三方机构授权或认可的方式使用本服务的；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("4、因病毒、木马、恶意程序攻击、网络拥堵、系铳不稳定 、系统或设备故障、通讯故障、电力故障或政府行为等原因；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("5、由于黑客攻击、网络供应商技术调整或故障、网站升级、采集数据源系统方面的问题等原因而造成的本服务中断或延迟；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("6、因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可杭力之因素，造成本服务系统障碍不能执行业务的。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("尽管有前款约定，睿普征信及第三方机构将采取合理行动积极促使服务恢复正常。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第六章 隐私权保护及授权条款\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十条    您授权睿普征信到相应的个人信息源数据网站采集您的授权个人信息。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十一条    睿普征信不会保存您的密码。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十三条    睿普征信将对用户提供的信息严格保密，除具备下列情形之一外，不会向任何外部机构披露：\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1. 经过用户事先同意而披露，即在获得用户明确同意的情况下，睿普征信才会在授权范围内向特定被授权方披露用户的个人信息；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2. 应法律法规或公权力部门要求而披露，即睿普征信可能会根据法院、政府部门、上级监管机构等执法机构或法律法规的要求向其披露用户的个人信息；\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("3. 当睿普征信涉及合并、收购或资产出售等重大交易时，睿普征信有权依据交易的需要将用户信息提供给交易相对方及交易各方聘请的各中介机构(包括但不限于律师、会计师等)，会在任何个人信息进行转让或受其他隐私权政策约束之前，继续确保其保密性并对受到影响方进行及时通知。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第七章 条款的解释、法律适用及争端解决\n\n", CreateTableUtil.AgreementKeyBlackFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十四条    本协议是由您与睿普征信共同签订的，适用于您在本服务项下的全部活动。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十五条    如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，则应认为该条款可与本协议相分割，并可被尽可能接近各方意图的、能够保留本协议要求的经济目的的、有效的新条款所取代，而且，在此情况下，本协议的其他条款仍然完全有效并具有约束力。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十六条    本协议的有效性、履行与本协议所有事宜，将受中国法律管辖，任何争议仅适用中国法律。\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("第十七条    本协议签订地为中国苏州市。因本协议所引起的您与睿普征信的任何纠纷或争议，首先应友好协商解决，协商不成的，您在此完全同意将纠纷或争议提交中国苏州市中级人民法院\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("                                                                                                                                                             授 权 人："+ProtectingPrivacyUtil.nameEncrypt(String.valueOf(userCustomerMap.get("realname")))+"\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("                                                                                                                                                             身 份 证："+ProtectingPrivacyUtil.idEncrypt(String.valueOf(userCustomerMap.get("idcard")))+"\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        long lt = new Long(Long.valueOf(String.valueOf(jsonMap.get("timestamp"))));
                        Date date = new Date(lt);
                        paragraph = new Paragraph("                                                                                                                                                             日     期："+simpleDateFormat.format(date)+"\n\n", CreateTableUtil.AgreementGreyFont);
                        document.add(paragraph);
                        document.add(new Paragraph("\n\n\n\n\n"));

                        // 向文档中添加内容
                        paragraph = new Paragraph("淘宝报告",CreateTableUtil.headfontfirst);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));

                        //################################################基本信息############################################################
                        table = CreateTableUtil.createTable(4);
                        float[] columnWidths = { 1.5f, 3f, 1.5f, 3f};
                        table.setWidths(columnWidths);

                        //######第一行内容######
                        PdfPCell cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("真实姓名:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String name = String.valueOf(basicInfoMap.get("realName"));
                        if(!StringUtils.isEmpty(name)){
                            name = ProtectingPrivacyUtil.nameEncrypt(String.valueOf(basicInfoMap.get("realName")));
                        }
                        cell = CreateTableUtil.createCell(name,CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("登陆邮箱:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("email").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第三行内容######
                        cell = CreateTableUtil.createCell("身份证号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String identityNo = String.valueOf(basicInfoMap.get("identityNo"));
                        if(!StringUtils.isEmpty(identityNo)){
                            identityNo = ProtectingPrivacyUtil.idEncrypt(String.valueOf(basicInfoMap.get("identityNo")));
                        }
                        cell = CreateTableUtil.createCell(identityNo,CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("绑定手机:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String mobile = String.valueOf(basicInfoMap.get("mobile"));
                        if(!StringUtils.isEmpty(mobile)){
                            mobile = ProtectingPrivacyUtil.mobileEncrypt(mobile);
                        }
                        cell = CreateTableUtil.createCell(mobile,CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第四行内容######
                        cell = CreateTableUtil.createCell("用户号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("username").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("会员等级:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("vipLevel").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第五行内容######
                        cell = CreateTableUtil.createCell("昵称:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("nickName").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("成长值:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("growthValue").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第六行内容######
                        cell = CreateTableUtil.createCell("性别:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("gender").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("信用积分:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("creditScore").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第七行内容######
                        cell = CreateTableUtil.createCell("出生日期:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("birthday").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("好评率:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("favorableRate").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第八行内容######
                        cell = CreateTableUtil.createCell("认证渠道:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("identityChannel").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("安全等级:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("securityLevel").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第八行内容######
                        cell = CreateTableUtil.createCell("是否实名认证:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("identityStatus").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        table.addCell(cell);

                        document.add(table);

                        //################################################绑定的支付宝信息表############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        float[] alipayInfoColumnWidths = { 1.5f, 3f, 1.5f, 3f};
                        table.setWidths(alipayInfoColumnWidths);
                        //######第一行内容######
                        cell = CreateTableUtil.createCell("绑定的支付宝信息:", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("支付宝账号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("username").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("真实认证姓名:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String realName = String.valueOf(alipayInfoMap.get("realName"));
                        if(!StringUtils.isEmpty(realName)){
                            realName = ProtectingPrivacyUtil.nameEncrypt(realName);
                        }
                        cell = CreateTableUtil.createCell(realName,CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第三行内容######
                        cell = CreateTableUtil.createCell("邮箱:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("email").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("实名认证身份证号码:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String idCard = String.valueOf(alipayInfoMap.get("identityNo"));
                        if(!StringUtils.isEmpty(idCard)){
                            idCard = ProtectingPrivacyUtil.idEncrypt(String.valueOf(alipayInfoMap.get("identityNo")));
                        }
                        cell = CreateTableUtil.createCell(idCard,CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第四行内容######
                        cell = CreateTableUtil.createCell("绑定手机:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String phone = String.valueOf(alipayInfoMap.get("mobile"));
                        if(!StringUtils.isEmpty(phone)){
                            phone = ProtectingPrivacyUtil.idEncrypt(String.valueOf(alipayInfoMap.get("mobile")));
                        }
                        cell = CreateTableUtil.createCell(phone,CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("实名认证状态:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("identityStatus").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第五行内容######
                        cell = CreateTableUtil.createCell("账户余额:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("accBal").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("花呗可用额度:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("huabeiAvailableLimit").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第六行内容######
                        cell = CreateTableUtil.createCell("余额宝:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("yuebaoBal").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("花呗消费额度:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("huabeiLimit").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第七行内容######
                        cell = CreateTableUtil.createCell("历史累计收益:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("yuebaoHisIncome").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        table.addCell(cell);
                        document.add(table);

                        //################################################收货地址表############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        float[] addressesColumnWidths = { 1f, 5f, 1f, 1f};
                        table.setWidths(addressesColumnWidths);
                        //######第一行内容######
                        cell = CreateTableUtil.createCell("收货地址:", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("邮编", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("手机号", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        for(Object address:addressesArray){
                            Map<String,Object> addressMap = JSON.parseObject(address.toString());
                            //######第三行内容######
                            String consignee = String.valueOf(addressMap.get("name"));
                            if(!StringUtils.isEmpty(consignee)){
                                consignee = ProtectingPrivacyUtil.nameEncrypt(consignee);
                            }
                            cell = CreateTableUtil.createCell(consignee, CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(addressMap.get("address").toString(), CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(addressMap.get("zipCode").toString(), CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                            String recieverPhone = String.valueOf(addressMap.get("mobile"));
                            if(StringUtils.isEmpty(recieverPhone)){
                                recieverPhone = ProtectingPrivacyUtil.mobileEncrypt(recieverPhone);
                            }
                            cell = CreateTableUtil.createCell(recieverPhone, CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                        }

                        document.add(table);

                        //################################################订单详情表############################################################
                        table = CreateTableUtil.createTable(10);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        float[] orderDetailsColumnWidths = { 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
                        table.setWidths(orderDetailsColumnWidths);

                        //######第一行内容######
                        cell = CreateTableUtil.createCell("订单详情", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle);
                        cell.setColspan(10); //合并列
                        table.addCell(cell);
                        document.add(table);

                        for(Object orderDetails:orderDetailsArray){
                            table = CreateTableUtil.createTable(10);
                            table.setWidths(orderDetailsColumnWidths);
                            Map<String,Object> orderDetailsMap = JSON.parseObject(orderDetails.toString());
                            //######第二行内容######
                            cell = CreateTableUtil.createCell("订单号 :    "+orderDetailsMap.get("orderId").toString(), CreateTableUtil.keyfonfirst);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(10); //合并列
                            table.addCell(cell);
                            //######第三行内容######
                            cell = CreateTableUtil.createCell("订单金额 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(orderDetailsMap.get("orderAmt").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("订单时间 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(orderDetailsMap.get("orderTime").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(7); //合并列
                            table.addCell(cell);
                            //######第四行内容######
                            cell = CreateTableUtil.createCell("订单状态 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(orderDetailsMap.get("orderStatus").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);

                            Map<String,Object> logisticsInfoMap = JSON.parseObject(orderDetailsMap.get("logisticsInfo").toString());//获取物流信息

                            cell = CreateTableUtil.createCell("收货人信息 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            String receivePersonName = String.valueOf(logisticsInfoMap.get("receivePersonName"));
                            if(!StringUtils.isEmpty(receivePersonName)){
                                receivePersonName = ProtectingPrivacyUtil.nameEncrypt(receivePersonName);
                            }
                            String receivePersonMobile = String.valueOf(logisticsInfoMap.get("receivePersonMobile"));
                            if(!StringUtils.isEmpty(receivePersonMobile)){
                                receivePersonMobile = ProtectingPrivacyUtil.mobileEncrypt(receivePersonMobile);
                            }
                            cell = CreateTableUtil.createCell(receivePersonName+"  "+receivePersonMobile, CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(2);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("receiveAddress").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(5); //合并列
                            table.addCell(cell);

                            //******商品信息******
                            JSONArray itemsArray = JSON.parseArray(orderDetailsMap.get("items").toString());//订单信息，商品信息、商家信息
                            for(Object item:itemsArray){
                                Map<String,Object> itemMap = JSON.parseObject(item.toString());

                                cell = CreateTableUtil.createCell("购买商品信息", CreateTableUtil.keyfonfirst);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(10); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("商品ID", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商品名称", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(4); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商品单价", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商品数量", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell(itemMap.get("itemId").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(itemMap.get("itemName").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(4); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(itemMap.get("itemPrice").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(itemMap.get("itemQuantity").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                            }
                            //******物流信息******
                            cell = CreateTableUtil.createCell("物流信息", CreateTableUtil.keyfonfirst);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(10); //合并列
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("运送方式", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(2); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("物流公司", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(4); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("送货单号", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(4); //合并列
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("deliverType").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setBorderWidthBottom(0.1f);//表格最下方表格线条
                            cell.setColspan(2); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("deliverCompany").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setBorderWidthBottom(0.1f);//表格最下方表格线条
                            cell.setColspan(4); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("deliverId").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            //cell.setBorderWidthRight(0.1f);//表格最右测表格线条
                            cell.setBorderWidthBottom(0.1f);//表格最下方表格线条
                            cell.setColspan(4); //合并列
                            table.addCell(cell);
                            document.add(table);
                        }

                        paragraph = new Paragraph("\n\n", CreateTableUtil.keyGreyFont);
                        document.add(paragraph);
                        //备注
                        paragraph = new Paragraph("免责声明 : \n", CreateTableUtil.keyGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1.本报告为睿普征信根据信息主体的授权将查询到的信息进行收集、归纳，未对该信息作任何的修改，睿普征信不对信息的有效性、准确性、完整性做出任何承诺和保证。您若对该报告有任何疑问的，欢迎及时向睿普征信及信息源提出异议。\n", CreateTableUtil.textfont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2.睿普征信对本报告不作任何明示或暗示的保证，请您自行承担相关的决策风险。请您按照约定的范围使用本报告，不得将本报告提供给第三人，否则后果自负。\n", CreateTableUtil.textfont);
                        document.add(paragraph);

                        // 关闭文档
                        document.close();
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
