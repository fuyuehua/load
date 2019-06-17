package com.rip.load.utils.pdfUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rip.load.otherPojo.honeyportData.*;
import com.rip.load.otherPojo.vehicleDetailsEnquiry.VehicleDetailsEnquiry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 风控报告
 * @Author: FYH
 * @Date: Created in 17:12 2019/5/28
 * @Modified:
 */
public class WindControlReportUtil {
    /**
     * 运营商信用报告
     *
     * @throws IOException
     * @throws DocumentException
     */
    public static Boolean windControlReport( String json, String savePath ) {
        try {
            //################################################获取数据############################################################
            Map<String, Object> jsonMap = JSON.parseObject(json);
            if (jsonMap.get("code") != null && Integer.valueOf(jsonMap.get("code").toString()) == 200) {
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
                //################################################封面############################################################
                PdfPTable table = CreateTableUtil.createTable(1);
                Image img = null;
                try{
                    img = Image.getInstance("../img/homepage.jpg");//图片的地址
                }catch (Exception e){
                }
                if(img == null)
                    img = Image.getInstance("/usr/local/tomcatfangdi-8088/webapps/img/homepage.jpg");//图片的地址

                img.scaleAbsolute(CreateTableUtil.mmTopx(200), CreateTableUtil.mmTopx(297));
                PdfPCell photoCell = new PdfPCell(img);
                photoCell.setBorder(0);
                photoCell.setHorizontalAlignment(1);
                table.addCell(photoCell);
                document.add(table);

                //################################################授权协议############################################################
                Map<String, Object> resultMap = JSON.parseObject(jsonMap.get("result").toString());
                JSONArray riskRuleItemsArray = JSONArray.parseArray(resultMap.get("riskRuleItems").toString());//风控规则
                JSONArray itemListArray = JSONArray.parseArray(resultMap.get("itemList").toString());//用户详情
                Map<String, Object> userCustomerMap = JSON.parseObject(String.valueOf(resultMap.get("userCustomer")));//用户基本信息

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

                //################################################大标题############################################################
                paragraph = new Paragraph("综合风控报告", CreateTableUtil.headfontfirst);
                paragraph.setAlignment(1);
                document.add(paragraph);
                document.add(new Paragraph("\n"));
                //################################################基本验证############################################################
                table = CreateTableUtil.createTable(6);
                PdfPCell cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(6); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("realname")), CreateTableUtil.textfont);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcardSex")), CreateTableUtil.textfont);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("民族", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcardNation")), CreateTableUtil.textfont);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcardAddress")), CreateTableUtil.textfont);
                cell.setColspan(5);
                table.addCell(cell);

                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("身份证号", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(2);
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcard")), CreateTableUtil.textfont);
                cell.setColspan(5);
                table.addCell(cell);

                int num = 0;
                boolean flag = true;
                cell = CreateTableUtil.createCell("身份证二要素核验：", CreateTableUtil.textfont);
                table.addCell(cell);
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("1".equals(String.valueOf(itemMap.get("type")))) {//身份证二要素核验
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.headRedfont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell(result, font);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && num >= itemListArray.size()) {
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("手机号：", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(3);
                table.addCell(cell);

                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("cellphone")), CreateTableUtil.textfont);
                cell.setColspan(2);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("2".equals(String.valueOf(itemMap.get("type")))) {//运营商三要素
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        cell = CreateTableUtil.createCell("".equals(String.valueOf(dataMap.get("isp")))?"未知":String.valueOf(dataMap.get("isp")), CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);

                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.textRedFont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(result, font);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object itemOne : itemListArray) {
                    Map<String, Object> itemOneMap = JSON.parseObject(String.valueOf(itemOne));
                    if ("3".equals(String.valueOf(itemOneMap.get("type")))) {//客户手机在网时长
                        Map<String, Object> resultJsonOneMap = JSON.parseObject(String.valueOf(itemOneMap.get("resultJson")));
                        Map<String, Object> dataOneMap = JSON.parseObject(String.valueOf(resultJsonOneMap.get("data")));
                        cell = CreateTableUtil.createCell(String.valueOf(dataOneMap.get("OUTPUT1")), CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && itemListArray.size() <= num) {
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("紧急联系人A", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(3);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("姓名：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("aRealname")), CreateTableUtil.textfont);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("手机号：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("aPhone")), CreateTableUtil.textfont);
                cell.setColspan(2);
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("operatorTwoElementsA".equals(String.valueOf(itemMap.get("type")))) {//紧急联系人A是否本人号码（运营商三要素）
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.textRedFont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(result, font);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("".equals(String.valueOf(dataMap.get("isp"))) ? "未知" : String.valueOf(dataMap.get("isp")), CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if(flag && num>=itemListArray.size()){
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                    }
                }

                num = 0;
                flag = true;
                for (Object itemOne : itemListArray) {
                    Map<String, Object> itemOneMap = JSON.parseObject(String.valueOf(itemOne));
                    if ("inTheNetworkTimeA".equals(String.valueOf(itemOneMap.get("type")))) {//客户手机在网时长
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        Map<String, Object> resultJsonOneMap = JSON.parseObject(String.valueOf(itemOneMap.get("resultJson")));
                        Map<String, Object> dataOneMap = JSON.parseObject(String.valueOf(resultJsonOneMap.get("data")));
                        cell = CreateTableUtil.createCell(String.valueOf(dataOneMap.get("OUTPUT1")), CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && itemListArray.size() <= num) {
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("紧急联系人B", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(3);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("姓名：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("bRealname")), CreateTableUtil.textfont);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("手机号：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("bPhone")), CreateTableUtil.textfont);
                cell.setColspan(2);
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("operatorTwoElementsB".equals(String.valueOf(itemMap.get("type")))) {//紧急联系人A是否本人号码（运营商三要素）
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if(dataMap == null)
                        {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }else if(dataMap.get("key") == null){
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        } else if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.textRedFont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(result, font);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        if(dataMap != null) {
                            cell = CreateTableUtil.createCell("".equals(String.valueOf(dataMap.get("isp"))) ? "未知" : String.valueOf(dataMap.get("isp")), CreateTableUtil.textfont);
                        }else{
                            cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        }
                        cell.setColspan(2);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if(flag && num>=itemListArray.size()){
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                    }
                }

                num = 0;
                flag = true;
                for (Object itemOne : itemListArray) {
                    Map<String, Object> itemOneMap = JSON.parseObject(String.valueOf(itemOne));
                    if ("inTheNetworkTimeB".equals(String.valueOf(itemOneMap.get("type")))) {//手机在网时长
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        Map<String, Object> resultJsonOneMap = JSON.parseObject(String.valueOf(itemOneMap.get("resultJson")));
                        Map<String, Object> dataOneMap = JSON.parseObject(String.valueOf(resultJsonOneMap.get("data")));
                        cell = CreateTableUtil.createCell(String.valueOf(dataOneMap.get("OUTPUT1")), CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && itemListArray.size() <= num) {
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################风控规则############################################################
                table = CreateTableUtil.createTable(6);
                table.setSpacingBefore(5f); //和上一个表格间距5f
//                float[] bisicInfocolumnWidths = {1f, 1f, 1f, 1f, 1f, 1f};
//                table.setWidths(bisicInfocolumnWidths);
                //######第一行内容######
                cell = CreateTableUtil.createCell("风控规则", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(6); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("规则说明", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setColspan(4);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("分值", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell("测评结果", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);

                for (Object riskRuleItems : riskRuleItemsArray) {
                    Map<String, Object> riskRuleItemsMap = JSON.parseObject(String.valueOf(riskRuleItems));
                    Map<String, Object> riskRuleMap = JSON.parseObject(String.valueOf(riskRuleItemsMap.get("riskRule")));
                    Map<String, Object> ruleMap = JSON.parseObject(String.valueOf(riskRuleMap.get("rule")));
                    String info = "";//规则
                    String grade = ""; //分值
                    String f = ""; //是否通过（1：通过，0：不通过）
                    if ("ageCheck".equals(String.valueOf(ruleMap.get("method")))) {//年龄验证
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA"))).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramB")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("idCardElements".equals(String.valueOf(ruleMap.get("method")))) {//身份证核验
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("operatorThreeElements".equals(String.valueOf(ruleMap.get("method")))) {//客户运营商三要素
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("operatorTwoElementsA".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人A运营商三要素
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("operatorTwoElementsB".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人B运营商三要素
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("inTheNetworkTime".equals(String.valueOf(ruleMap.get("method")))) {//手机在网时长
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("inTheNetworkTimeA".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人A手机在网时长
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("inTheNetworkTimeB".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人B手机在网时长
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("personalEnterprise".equals(String.valueOf(ruleMap.get("method")))) {//个人名下关联企业起诉
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("personalEnterprise2".equals(String.valueOf(ruleMap.get("method")))) {//个人名下关联企业行政处罚
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("businessData".equals(String.valueOf(ruleMap.get("method")))) {//企业工商数据查询起诉
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("businessData2".equals(String.valueOf(ruleMap.get("method")))) {//企业工商数据查询行政处罚
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("businessData3".equals(String.valueOf(ruleMap.get("method")))) {//企业工商数据查询严重违法
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("personalComplaintInquiryC".equals(String.valueOf(ruleMap.get("method")))) {//个人涉诉
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    }

                    Font font;
                    if ("1".equals(String.valueOf(riskRuleItemsMap.get("flag")))) {
                        f = "通过";
                        font = CreateTableUtil.textGreenFont;
                    } else {
                        f = "不通过";
                        font = CreateTableUtil.textRedFont;
                    }
                    cell = CreateTableUtil.createCell(info, CreateTableUtil.textfont);
                    cell.setColspan(4);
                    table.addCell(cell);
                    cell = CreateTableUtil.createCell(grade, CreateTableUtil.textfont);
                    table.addCell(cell);
                    cell = CreateTableUtil.createCell(f, font);
                    table.addCell(cell);
                }
                document.add(table);

                //################################################蜜罐数据############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("综合风险", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("honeyportData".equals(String.valueOf(itemMap.get("type")))) {//蜜罐数据
                        HoneyportData honeyportData = JSONObject.parseObject(String.valueOf(itemMap.get("resultJson")),HoneyportData.class);
                        if("10000".equals(honeyportData.getCode())) {
                            Result result = honeyportData.getResult();
                            Data data = result.getData();
                            if (result.getSuccess()) {
//                                cell = CreateTableUtil.createCell("蜜罐编号",  CreateTableUtil.textfont); //表格中添加的文字
//                                cell.setBackgroundColor(CreateTableUtil.tableBody);
//                                table.addCell(cell);
//                                cell = CreateTableUtil.createCell(data.getUser_grid_id(),  CreateTableUtil.textfont); //表格中添加的文字
//                                cell.setColspan(3);
//                                table.addCell(cell);
//                                cell = CreateTableUtil.createCell("授权机构",  CreateTableUtil.textfont); //表格中添加的文字
//                                cell.setBackgroundColor(CreateTableUtil.tableBody);
//                                table.addCell(cell);
//                                cell = CreateTableUtil.createCell(data.getAuth_org(),  CreateTableUtil.textfont); //表格中添加的文字
//                                cell.setColspan(3);
//                                table.addCell(cell);
                                UserBasic userBasic = data.getUser_basic();//基本信息
                                cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("姓名",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_name(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("年龄",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_age(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("性别",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_gender(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("身份证号",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_idcard(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("城市",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_city(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("身份证是否有效",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("true".equals(userBasic.getUser_idcard_valid())?"是":"否",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("出生省份",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_phone_province(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("出生城市",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_city(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("手机号",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_phone(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("手机号城市",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_phone_city(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("运营商",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_phone_operator(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("手机号归属地",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBasic.getUser_phone_province(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                UserBlacklist userBlacklist = data.getUser_blacklist();//黑名单信息
                                cell = CreateTableUtil.createCell("黑名单信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("身份证和姓名是否在黑名单",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("true".equals(userBlacklist.getBlacklist_name_with_idcard())?"是":"否",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBlacklist.getBlacklist_update_time_name_idcard(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                List<String> blacklistCategoryList = userBlacklist.getBlacklist_category();//黑名单类型
                                if(blacklistCategoryList.size()>0){
                                    num = 0;
                                    StringBuilder stb = new StringBuilder();
                                    for(String blacklistCategory:blacklistCategoryList){
                                        if(num > 0){
                                            stb.append("，");
                                        }
                                        stb.append(blacklistCategory);
                                        num++;
                                    }
                                    cell = CreateTableUtil.createCell("黑名单类型",  CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setBackgroundColor(CreateTableUtil.tableBody);
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(stb.toString(),  CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(7);
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("手机号和姓名是否在黑名单",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("true".equals(userBlacklist.getBlacklist_name_with_phone())?"是":"否",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userBlacklist.getBlacklist_update_time_name_phone(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                List<BlacklistDetails> blacklistDetailsList = userBlacklist.getBlacklist_details();
                                if(blacklistDetailsList.size()>0){
                                    for(BlacklistDetails blacklistDetails:blacklistDetailsList){
                                        cell = CreateTableUtil.createCell(blacklistDetails.getDetails_key(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(blacklistDetails.getDetails_value(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                    }
                                }

                                UserGray userGray = data.getUser_gray();//灰度分
                                cell = CreateTableUtil.createCell("灰度分", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("手机号",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getUser_phone(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("一阶联系人总数",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getContacts_class1_cnt(),  CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("直接联系人在黑名单的数量",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getContacts_class1_blacklist_cnt(),  CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("间接联系人在黑名单的数量",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getContacts_class2_blacklist_cnt(),  CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("引起二阶黑名单人数",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getContacts_router_cnt(),  CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("引起占比(引起二阶黑名单人数/一阶联系人总数)",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getContacts_router_ratio(),  CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("灰度分",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userGray.getPhone_gray_score(),  CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);


                                UserIdcardIuspicion userIdcardIuspicion = data.getUser_idcard_suspicion();//身份证存疑
                                List<IdcardWithOtherNames> idcardWithOtherNamesList = userIdcardIuspicion.getIdcard_with_other_names();//用这个身份证号码绑定的其他姓名
                                if(idcardWithOtherNamesList.size()>0){
                                    cell = CreateTableUtil.createCell("身份证绑定的其他姓名", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for(IdcardWithOtherNames idcardWithOtherNames:idcardWithOtherNamesList){
                                        cell = CreateTableUtil.createCell("绑定姓名",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherNames.getSusp_name(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherNames.getSusp_updt(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }

                                List<IdcardWithOtherPhones> idcardWithOtherPhonesList = userIdcardIuspicion.getIdcard_with_other_phones();//身份证绑定的其他手机号码
                                if(idcardWithOtherPhonesList.size()>0){
                                    cell = CreateTableUtil.createCell("身份证绑定的其他手机号码", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for(IdcardWithOtherPhones idcardWithOtherPhones:idcardWithOtherPhonesList){
                                        cell = CreateTableUtil.createCell("手机号码",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherPhones.getSusp_phone(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("运营商",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherPhones.getSusp_phone_operator(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("手机号城市",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherPhones.getSusp_phone_city(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("手机号归属地",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherPhones.getSusp_phone_province(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardWithOtherPhones.getSusp_updt(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }

                                List<IdcardAppliedInOrgs> idcardAppliedInOrgsList = userIdcardIuspicion.getIdcard_applied_in_orgs();//身份证在那些类型的机构中使用过
                                if(idcardAppliedInOrgsList.size()>0){
                                    cell = CreateTableUtil.createCell("身份证在那些类型的机构中使用过", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for (IdcardAppliedInOrgs idcardAppliedInOrgs:idcardAppliedInOrgsList){
                                        cell = CreateTableUtil.createCell("机构所属分类",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardAppliedInOrgs.getSusp_org_type(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(idcardAppliedInOrgs.getSusp_updt(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }

                                UserPhoneSuspicion userPhoneSuspicion = data.getUser_phone_suspicion();//手机号存疑
                                List<PhoneWithOtherIdcards> phoneWithOtherIdcardsList = userPhoneSuspicion.getPhone_with_other_idcards(); //手机号码绑定的其他身份证
                                if(phoneWithOtherIdcardsList.size()>0){
                                    cell = CreateTableUtil.createCell("手机号码绑定的其他身份证", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for(PhoneWithOtherIdcards phoneWithOtherIdcards:phoneWithOtherIdcardsList){
                                        cell = CreateTableUtil.createCell("身份证号",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(phoneWithOtherIdcards.getSusp_idcard(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(phoneWithOtherIdcards.getSusp_updt(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }

                                List<PhoneWithOtherNames> phoneWithOtherNamesList = userPhoneSuspicion.getPhone_with_other_names(); //手机号码绑定的其他姓名
                                if(phoneWithOtherNamesList.size()>0){
                                    cell = CreateTableUtil.createCell("手机号码绑定的其他身份证", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for(PhoneWithOtherNames phoneWithOtherNames:phoneWithOtherNamesList){
                                        cell = CreateTableUtil.createCell("身份证号",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(phoneWithOtherNames.getSusp_name(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(phoneWithOtherNames.getSusp_updt(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }

                                List<PhoneAppliedInOrgs> phoneAppliedInOrgsList = userPhoneSuspicion.getPhone_applied_in_orgs(); //手机号码在那些类型的机构中使用过
                                if(phoneAppliedInOrgsList.size()>0){
                                    cell = CreateTableUtil.createCell("手机号码在那些类型的机构中使用过", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for(PhoneAppliedInOrgs phoneAppliedInOrgs:phoneAppliedInOrgsList){
                                        cell = CreateTableUtil.createCell("身份证号",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(phoneAppliedInOrgs.getSusp_org_type(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(phoneAppliedInOrgs.getSusp_updt(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }

                                List<UserSearchedHistoryByOrgs> userSearchedHistoryByOrgsList = data.getUser_searched_history_by_orgs(); //用户被机构查询历史
                                if(userSearchedHistoryByOrgsList.size()>0){
                                    cell = CreateTableUtil.createCell("用户被机构查询历史", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                    for(UserSearchedHistoryByOrgs userSearchedHistoryByOrgs:userSearchedHistoryByOrgsList){
                                        cell = CreateTableUtil.createCell("机构类型",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(userSearchedHistoryByOrgs.getSearched_org(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(userSearchedHistoryByOrgs.getSearched_date(),  CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("是否是本机构查询",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("true".equals(userSearchedHistoryByOrgs.getOrg_self())?"是":"否",  CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                    UserSearchedStatistic userSearchedStatistic = data.getUser_searched_statistic();//被机构查询数量
                                    cell = CreateTableUtil.createCell("被机构查询数量",  CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setBackgroundColor(CreateTableUtil.tableBody);
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(userSearchedStatistic.getSearched_org_cnt(),  CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(7);
                                    table.addCell(cell);
                                }

                                UserRegisterOrgs userRegisterOrgs = data.getUser_register_orgs();//用户注册信息情况
                                List<RegisterOrgs> registerOrgsList = userRegisterOrgs.getRegister_orgs();//注册机构
                                List<RegisterOrgsStatistics> registerOrgsStatisticsList = userRegisterOrgs.getRegister_orgs_statistics();//统计
                                cell = CreateTableUtil.createCell("用户注册信息情况", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("手机号",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userRegisterOrgs.getPhone_num(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("计数",  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(userRegisterOrgs.getRegister_cnt(),  CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                if(registerOrgsList.size()>0){
                                    //TODO 注册结构
                                }
                                if (registerOrgsStatisticsList.size()>0){
                                    for(RegisterOrgsStatistics registerOrgsStatistics:registerOrgsStatisticsList){
                                        cell = CreateTableUtil.createCell("计数",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(registerOrgsStatistics.getCount(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("类型",  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(registerOrgsStatistics.getLabel(),  CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }
                                flag = false;
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################企业工商数据############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("企业工商数据", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                cell.setColspan(8); //合并列
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("5".equals(String.valueOf(itemMap.get("type")))) {//企业工商数据查
                        // 获取数据
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        if("true".equals(String.valueOf(resultJsonMap.get("success")))){
                            Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                            if("EXIST".equals(String.valueOf(dataMap.get("status")))){
                                Map<String, Object> basicMap = JSON.parseObject(String.valueOf(dataMap.get("basic")));//基本信息
                                JSONArray shareholderArray = JSONArray.parseArray(String.valueOf(dataMap.get("shareholder")));//股东信息
                                JSONArray shareholderPersonsArray = JSONArray.parseArray(String.valueOf(dataMap.get("shareholderPersons")));//高管信息
                                JSONArray legalPersonInvestsArray = JSONArray.parseArray(String.valueOf(dataMap.get("legalPersonInvests")));//法人对外投资信息
                                JSONArray legalPersonPostionsArray = JSONArray.parseArray(String.valueOf(dataMap.get("legalPersonPostions")));//法人对外任职信息
                                JSONArray enterpriseInvestsArray = JSONArray.parseArray(String.valueOf(dataMap.get("enterpriseInvests")));//企业对外投资信息
                                JSONArray alterInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("alterInfos")));//变更信息
                                JSONArray filiationsArray = JSONArray.parseArray(String.valueOf(dataMap.get("filiations")));//分支机构信息
                                JSONArray morguaInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("morguaInfos")));//动产抵押物信息
                                JSONArray punishBreaksArray = JSONArray.parseArray(String.valueOf(dataMap.get("punishBreaks")));//失信被执行人信息
                                JSONArray punishedArray = JSONArray.parseArray(String.valueOf(dataMap.get("punished")));//被执行人信息
                                JSONArray sharesFrostsArray = JSONArray.parseArray(String.valueOf(dataMap.get("sharesFrosts")));//股权冻结历史信息
                                JSONArray liquidationsArray = JSONArray.parseArray(String.valueOf(dataMap.get("liquidations")));//清算信息
                                JSONArray caseInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("caseInfos")));//行政处罚历史信息
                                JSONArray mortgageAlterArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgageAlter")));//动产抵押-变更信息
                                JSONArray mortgageCancelsArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgageCancels")));//动产抵押-注销信息
                                JSONArray mortgageDebtorsArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgageDebtors")));//动产抵押-被担保主债权信息
                                JSONArray mortgagePersonsArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgagePersons")));//动产抵押-抵押人信息
                                JSONArray breakLawArray = JSONArray.parseArray(String.valueOf(dataMap.get("breakLaw")));//严重违法信息
                                JSONArray exceptionListsArray = JSONArray.parseArray(String.valueOf(dataMap.get("exceptionLists")));//企业异常名录
                                JSONArray orgBasicsArray = JSONArray.parseArray(String.valueOf(dataMap.get("orgBasics")));//组织机构代码
                                JSONArray equityInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("equityInfos")));//股权出质信息
                                JSONArray equityChangeInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("equityChangeInfos")));//股权出质信息-变更信息
                                JSONArray cancellationOfInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("cancellationOfInfos")));//股权出质信息-注销信息
                                JSONArray tradeMarksArray = JSONArray.parseArray(String.valueOf(dataMap.get("tradeMarks")));//注册商标

                                cell = CreateTableUtil.createCell("企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("原注册号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("oRigegNo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("法定代表人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("fName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("实收资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("recCap")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营状态(在营、吊销、注销、其他)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("开业日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("esDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("经营期限自", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opFrom")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营期限至", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opTo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("住址", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("dom")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regorg")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("许可经营项目", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("abuItem")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("一般经营项目", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("cbuItem")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("经营(业务)范围", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opScope")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营(业务)范围及方式", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opScoandForm")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("最后年检年度", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("anCheYear")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("changDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("最后年检日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("anCheDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(7);
                                table.addCell(cell);


                                cell = CreateTableUtil.createCell("行业门类代码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryphyCode")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("行业门类名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryphyName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);


                                cell = CreateTableUtil.createCell("国民经济行业代码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryCoCode")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("国民经济行业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryCoName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("统一社会信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("核准日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("apprdate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("行业门类代码及名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("industryPhyAll")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注册地址行政区编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regOrgCode")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("企业英文名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entNameEng")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营业务范围", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("zsOpScope")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("住所所在行政区划", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("domDistrict")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("联系电话", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("tel")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("国民经济行业代码及名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("industryCoAll")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("员工人数", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("empNum")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("所在省份", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regOrgProvince")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("省节点编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("sextNodeNum")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("经营场所", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opLoc")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(7);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("股东信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("股东名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("股东详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(shareholderArray.size()>0){
                                    for(Object shareholder:shareholderArray){
                                        Map<String,Object> shareholderMap = JSON.parseObject(String.valueOf(shareholder));
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("shaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        cell.setRowspan(4);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资金额(万元):", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("subConAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("认缴出资币种:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资方式:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("conForm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资比例:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("fundedRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资日期：", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("conDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("国别:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("country")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("股东总数量:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("invaMount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("统一信用代码:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("高管信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("职位", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("性别:", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("总人数", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(shareholderPersonsArray.size()>0){
                                    for(Object shareholderPersons:shareholderPersonsArray){
                                        Map<String,Object> shareholderPersonsMap = JSON.parseObject(String.valueOf(shareholderPersons));
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("position")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("personAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("法人对外投资信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("投资企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("企业详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(legalPersonInvestsArray.size()>0){
                                    for(Object legalPersonInvests:legalPersonInvestsArray){
                                        Map<String,Object> legalPersonInvestsMap = JSON.parseObject(String.valueOf(legalPersonInvests));
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(9);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("经营状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("subConAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("curRenCy")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资方式", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("conForm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资比例", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("fundedRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业总数量", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(4);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("开业时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("esDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册地行政编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("postCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }


                                cell = CreateTableUtil.createCell("法人其他任职信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("任职企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("任职详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(legalPersonPostionsArray.size()>0){
                                    for(Object legalPersonPostions:legalPersonPostionsArray){
                                        Map<String,Object> legalPersonPostionsMap = JSON.parseObject(String.valueOf(legalPersonPostions));
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(7);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("职务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("position")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("是否法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("lerepSign")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业总数", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("经营状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("开业日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("esDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册地行政编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("postCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业对外投资信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("投资企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("企业详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(enterpriseInvestsArray.size()>0){
                                    for(Object enterpriseInvests:enterpriseInvestsArray){
                                        Map<String,Object> enterpriseInvestsMap = JSON.parseObject(String.valueOf(enterpriseInvests));
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(9);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("经营状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("subConAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("conGroCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资方式", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("conForm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资比例", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("fundedRation")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业总数量", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("binvvAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("creditCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("开业时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("openingDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册地行政编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("postCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("变更信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                num = 1;
                                if(alterInfosArray.size()>0) {
                                    for (Object alterInfos : alterInfosArray) {
                                        cell = CreateTableUtil.createCell("NO"+num, CreateTableUtil.headfontthird); //表格中添加的文字
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        cell.setRowspan(4);
                                        table.addCell(cell);

                                        Map<String,Object> alterInfosMap = JSON.parseObject(String.valueOf(alterInfos));
                                        cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                        //cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("变更事项", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altItem")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("变更前内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altBe")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("变更后内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altAf")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        num++;
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }


                                cell = CreateTableUtil.createCell("分支机构信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("分支机构名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("分支机构详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(filiationsArray.size()>0){
                                    for(Object filiations:filiationsArray){
                                        Map<String,Object> filiationsMap = JSON.parseObject(String.valueOf(filiations));
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(4);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brRegNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构负责人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brPrincipal")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("一般经营项目", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("cbuItme")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构地址", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brAddr")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brnCreditCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brnRegOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("主体身份代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brpripId")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押物信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("抵押物名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("综合信息(数量、质量、状况、所在地等情况)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("备注", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("所有权或使用权归属", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);

                                if(morguaInfosArray.size()>0) {
                                    for (Object morguaInfos : morguaInfosArray) {
                                        Map<String, Object> filiationsMap = JSON.parseObject(String.valueOf(morguaInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("guaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("comprehensiveDetail")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("remark")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("owner")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("失信被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishBreaksArray.size()>0){
                                    for(Object punishBreaks:punishBreaksArray){
                                        Map<String,Object> punishBreaksMap = JSON.parseObject(String.valueOf(punishBreaks));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(11);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("caseState")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信人类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人/负责人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("businessEntity")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("公布时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("publishDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("courName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行依据文号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistId")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行依据单位", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistUnit")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信被执行人行为具体情形", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("disruptTypeName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("被执行人的履行情况", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performAnce")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("已履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performedPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("未履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("unPerformPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("退出日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("exitDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("关注次数", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("focusNumber")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("法律文书确定的义务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("duty")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishedArray.size()>0){
                                    for(Object punished:punishedArray){
                                        Map<String,Object> punishedMap = JSON.parseObject(String.valueOf(punished));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(6);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseState")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信人类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("courtName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行标的（元）", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("execMoney")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("关注次数", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("focusNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }
                                document.add(table);

                                table = CreateTableUtil.createTable(9);
                                cell = CreateTableUtil.createCell("股权冻结历史信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结起始日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结截至日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻说明", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(sharesFrostsArray.size()>0) {
                                    for (Object sharesFrosts : sharesFrostsArray) {
                                        Map<String, Object> sharesFrostsMap = JSON.parseObject(String.valueOf(sharesFrosts));
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froDocNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froFrom")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froTo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawDocNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawComment")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("清算信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算责任人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算负责人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算组成员", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算完结情况", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算完结日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("债务承接人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("债权承接人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("电话", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(liquidationsArray.size()>0) {
                                    for (Object liquidations : liquidationsArray) {
                                        Map<String, Object> liquidationsMap = JSON.parseObject(String.valueOf(liquidations));
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("ligEntity")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("ligPrincipal")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("liQMen")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("liGSt")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("ligEndDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("debtTranee")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("claimTranee")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("mobile")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("address")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("行政处罚历史信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                num = 0;
                                if(caseInfosArray.size()>0) {
                                    for (Object caseInfos : caseInfosArray) {
                                        Map<String, Object> caseInfosMap = JSON.parseObject(String.valueOf(caseInfos));
                                        cell = CreateTableUtil.createCell("NO"+num, CreateTableUtil.headfontthird); //表格中添加的文字
                                        cell.setRowspan(5);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚决定文书", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("签发日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecIssDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚机关名", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAuthCn")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("主要违法事实", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("illegFact")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚种类", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚种类中文", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penTypeDescription")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penContent")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("公示日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("announcementDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                        num++;
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-变更信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgageAlterArray.size()>0) {
                                    for (Object mortgageAlter : mortgageAlterArray) {
                                        Map<String, Object> mortgageAlterMap = JSON.parseObject(String.valueOf(mortgageAlter));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageAlterMap.get("alterDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageAlterMap.get("alterDetail")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(5);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageAlterMap.get("RegNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-注销信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgageCancelsArray.size()>0) {
                                    for (Object mortgageCancels : mortgageCancelsArray) {
                                        Map<String, Object> mortgageCancelsMap = JSON.parseObject(String.valueOf(mortgageCancels));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageCancelsMap.get("cancelDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageCancelsMap.get("cancelReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(5);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageCancelsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-被担保主债权信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("履行债务开始日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("履行债务结束日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("数额", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("担保范围", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("备注", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(2);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("种类", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgageDebtorsArray.size()>0) {
                                    for (Object mortgageDebtors : mortgageDebtorsArray) {
                                        Map<String, Object> mortgageDebtorsMap = JSON.parseObject(String.valueOf(mortgageDebtors));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("startDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("endtDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("amount")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("range")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("remark")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-抵押权人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("抵押权人证照/证件号码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("抵押权人证照/证件类型", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("所在地", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("抵押权人名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgagePersonsArray.size()>0) {
                                    for (Object mortgagePersons : mortgagePersonsArray) {
                                        Map<String, Object> mortgagePersonsMap = JSON.parseObject(String.valueOf(mortgagePersons));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("credentialNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("credentialType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("domain")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }
                                document.add(table);

                                table = CreateTableUtil.createTable(8);
                                cell = CreateTableUtil.createCell("严重违法信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入作出决定机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入作出决定文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出作出决定机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出作出决定文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(breakLawArray.size()>0) {
                                    for (Object breakLaw : breakLawArray) {
                                        Map<String, Object> breakLawMap = JSON.parseObject(String.valueOf(breakLaw));
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inRegOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inSn")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outRegOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outSn")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业异常名录", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(exceptionListsArray.size()>0){
                                    for(Object exceptionLists:exceptionListsArray){
                                        Map<String,Object> exceptionListsMap = JSON.parseObject(String.valueOf(exceptionLists));
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(5);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("列入日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("inDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("列入原因", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("inReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("移出日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("outDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("移出原因", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("outReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("列入机关名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("inOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("移出机关名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("outOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("shxydm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("机构组织代码", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("组织机构代码", CreateTableUtil.textfont); //
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("机构地址", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("组织机构名称", CreateTableUtil.textfont); //
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("质疑标志", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(orgBasicsArray.size()>0){
                                    for(Object orgBasics:orgBasicsArray){
                                        Map<String,Object> orgBasicsMap = JSON.parseObject(String.valueOf(orgBasics));
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("jgdm")), CreateTableUtil.textfont); //
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("jgdz")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("jgmc")), CreateTableUtil.textfont); //
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("zybz")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("股权出质信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("组织机构名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(equityInfosArray.size()>0){
                                    for(Object equityInfos:equityInfosArray){
                                        Map<String,Object> equityInfosMap = JSON.parseObject(String.valueOf(equityInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jgmc")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(7);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("办证机构", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("bzjg")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("代码证办证日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("bzrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人代表姓名", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("fddbr")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("组织机构代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jgdm")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构地址", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jgdz")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jglx")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("行政区划", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("xzqh")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("zcrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("代码证作废日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("zfrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("质疑标志", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("zybz")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出质股权数额", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnCzamt")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出质人证件/证件号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnCzcerno")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出质人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnCzper")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("公示日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("质权出质设立登记日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnRegdate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnRegno")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("质权人证件/证件号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnZqcerno")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("质权人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnZqper")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("关联内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(4);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("股权出质信息-变更信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("关联内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(equityChangeInfosArray.size()>0) {
                                    for (Object equityChangeInfos : equityChangeInfosArray) {
                                        Map<String, Object> equityChangeInfosMap = JSON.parseObject(String.valueOf(equityChangeInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(equityChangeInfosMap.get("stkPawnBgnr")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityChangeInfosMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityChangeInfosMap.get("stkPawnBgrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("股权出质信息-注销信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("关联内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(cancellationOfInfosArray.size()>0) {
                                    for (Object cancellationOfInfos : cancellationOfInfosArray) {
                                        Map<String, Object> cancellationOfInfosMap = JSON.parseObject(String.valueOf(cancellationOfInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(cancellationOfInfosMap.get("stkPawnRes")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(cancellationOfInfosMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(cancellationOfInfosMap.get("stkPawnDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("注册商标", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商标名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(tradeMarksArray.size()>0){
                                    for(Object tradeMarks:tradeMarksArray){
                                        Map<String,Object> tradeMarksMap = JSON.parseObject(String.valueOf(tradeMarks));
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(6);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("商标图片", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("申请日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("appDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("初审公告期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("checkDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("专用期(起始日期)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("beginDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("专用期(到期日期)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("endDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册码解密", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markCodeKey")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册公告期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("商标类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markTypeNew")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("流程", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markTypeNew")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("商标/服务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("typeDetailDes")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }
                                flag = false;
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################个人名下企业############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("个人名下企业", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("失信被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                cell.setColspan(8); //合并列
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("6".equals(String.valueOf(itemMap.get("type")))) {//个人名下企业
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        if("true".equals(String.valueOf(resultJsonMap.get("success")))) {
                            Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                            if ("EXIST".equals(String.valueOf(dataMap.get("status")))) {
                                JSONArray punishBreaksArray = JSONArray.parseArray(String.valueOf(dataMap.get("punishBreaks")));//失信被执行人信息
                                JSONArray punishedArray = JSONArray.parseArray(String.valueOf(dataMap.get("punished")));//被执行人信息
                                JSONArray caseInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("caseInfos")));//行政处罚历史信息
                                JSONArray corporatesArray = JSONArray.parseArray(String.valueOf(dataMap.get("corporates")));//企业法人信息
                                JSONArray corporateManagersArray = JSONArray.parseArray(String.valueOf(dataMap.get("corporateManagers")));//企业主要管理人员信息
                                JSONArray corporateShareholdersArray = JSONArray.parseArray(String.valueOf(dataMap.get("corporateShareholders")));//企业股东信息

                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishBreaksArray.size()>0){
                                    for(Object punishBreaks:punishBreaksArray){
                                        Map<String,Object> punishBreaksMap = JSON.parseObject(String.valueOf(punishBreaks));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(9);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信人类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人/负责人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("businessEntity")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("公布时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("publishDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("courName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行依据文号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistId")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行依据单位", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistUnit")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信被执行人行为具体情形", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("disruptTypeName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("被执行人的履行情况", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performAnce")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("已履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performedPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("未履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("unPerformPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishedArray.size()>0){
                                    for(Object punished:punishedArray){
                                        Map<String,Object> punishedMap = JSON.parseObject(String.valueOf(punished));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(6);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseState")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行标的(元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("execMoney")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("courtName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }
                                document.add(table);

                                table = CreateTableUtil.createTable(9);
                                cell = CreateTableUtil.createCell("行政处罚历史信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                num = 0;
                                if(caseInfosArray.size()>0) {
                                    for (Object caseInfos : caseInfosArray) {
                                        Map<String, Object> caseInfosMap = JSON.parseObject(String.valueOf(caseInfos));
                                        cell = CreateTableUtil.createCell("NO"+num, CreateTableUtil.headfontthird); //表格中添加的文字
                                        cell.setRowspan(8);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案由", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案发时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseTime")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案值", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseVal")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行类别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("exeSort")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件结果", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseResult")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚决定文书", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("签发日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecIssDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("主要违法事实", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("illegFact")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚依据", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penBasis")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚种类", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚结果", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penResult")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚执行情况", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penExeSt")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                        num++;
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业法人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("查询人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(8);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(corporatesArray.size()>0) {
                                    for (Object corporates : corporatesArray) {
                                        Map<String, Object> corporatesMap = JSON.parseObject(String.valueOf(corporates));
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("ryName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(4);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业主要管理人员信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("查询人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(8);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(corporateManagersArray.size()>0) {
                                    for (Object corporateManagers : corporateManagersArray) {
                                        Map<String, Object> corporateManagersMap = JSON.parseObject(String.valueOf(corporateManagers));
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("ryName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(4);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("职务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("position")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业股东信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("查询人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(8);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(corporateShareholdersArray.size()>0) {
                                    for (Object corporateShareholders : corporateShareholdersArray) {
                                        Map<String, Object> corporateShareholdersMap = JSON.parseObject(String.valueOf(corporateShareholders));
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("ryName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(5);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);


                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("认缴出资额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("subConam")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("认缴出资币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("currency")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资比例", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("fundedRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }
                                flag = false;
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################个人涉诉############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("个人涉诉", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("9".equals(String.valueOf(itemMap.get("type")))) {//个人涉诉
                        String[] redsultJsonArr = String.valueOf(itemMap.get("resultJson")).split("fengexian");
                        Boolean f = true;
                        if(redsultJsonArr.length>0){
                            for(int i=0;i<redsultJsonArr.length;i++){
                                Map<String, Object> resultJsonMap = JSON.parseObject(redsultJsonArr[0]);
                                if ("true".equals(String.valueOf(resultJsonMap.get("success")))) {
                                    Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                                    JSONArray pageDataArray = JSONArray.parseArray(String.valueOf(dataMap.get("pageData")));
                                    if (pageDataArray.size()>0) {
                                        Boolean ZXGG = true;
                                        Boolean CPWS = true;
                                        Boolean SXGG = true;
                                        Boolean BGT = true;
                                        Boolean KTGG = true;
                                        Boolean FYGG = true;
                                        Boolean WDHMD = true;
                                        Boolean AJLC = true;
                                        for(Object pageData : pageDataArray){
                                            if(f){//姓名和身份证信息只展示一次
                                                cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(dataMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("身份证号", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(dataMap.get("identityCard")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                f = false;
                                            }

                                            Map<String, Object> pageDataMap = JSON.parseObject(String.valueOf(pageData));//涉诉详情
                                            if("CPWS".equals(String.valueOf(pageDataMap.get("dataType")))){//裁判文书
                                                if(CPWS){
                                                    cell = CreateTableUtil.createCell("裁判文书",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    CPWS = false;
                                                }

                                                cell = CreateTableUtil.createCell("标题",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("title")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("裁判文书ID", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("类别", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("审结时间(时间戳)", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("审结时间(年月日格式)", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案件类别", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案由", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("判决结果", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("judgeResult")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人列表", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(8);
                                                cell.setHorizontalAlignment(1);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("当事人名称", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("当事人详情", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                JSONArray litigantsArray = JSONArray.parseArray(String.valueOf(pageDataMap.get("litigants")));
                                                for(Object litigants:litigantsArray){
                                                    Map<String,Object> litigantsMap = JSON.parseObject(String.valueOf(litigants));

                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setRowspan(4);
                                                    cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("当事人生日", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("birthday")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("当事人称号", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("title")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("律师事务所", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("lawFirm")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("address")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("当事人立场", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("standpoint")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("当事人身份证", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("identificationNO")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("当事人类型", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("委托辩护人", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("lawyer")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);
                                                }
                                            }else if("ZXGG".equals(String.valueOf(pageDataMap.get("dataType")))){//执行公告
                                                if(ZXGG){
                                                    cell = CreateTableUtil.createCell("执行公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    ZXGG = false;
                                                }
                                                cell = CreateTableUtil.createCell("标题",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("title")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案件状态",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseStatus")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被执行人姓名",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("身份证",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("identificationNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("立案时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("立案时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行标的",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("executionTarget")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                            }else if("SXGG".equals(String.valueOf(pageDataMap.get("dataType")))){//
                                                if(SXGG){
                                                    cell = CreateTableUtil.createCell("失信公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    SXGG = false;
                                                }

                                                cell = CreateTableUtil.createCell("法院公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("公告详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(13);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("履行情况",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("implementationStatus")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("依据案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("evidenceCode")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("做出执行依据单位",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("executableUnit")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("失信被执行人行为具体情形",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("specificCircumstances")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("生效法律文书确定的义务",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("obligations")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("省份",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("province")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被执行人姓名",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("身份证",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("identificationNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("年龄",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("age")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("性别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("gender")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("发布时间",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("postTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("立案时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("立案时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                            }else if("BGT".equals(String.valueOf(pageDataMap.get("dataType")))){//曝光台
                                                if(BGT){
                                                    cell = CreateTableUtil.createCell("曝光台",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    BGT = false;
                                                }

                                                cell = CreateTableUtil.createCell("曝光台ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("曝光台详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNo")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案由",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseCause")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("标的金额",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("executionTarget")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("发布时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("发布时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);
                                            }else if("KTGG".equals(String.valueOf(pageDataMap.get("dataType")))){//开庭公告
                                                if(KTGG){
                                                    cell = CreateTableUtil.createCell("开庭公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    KTGG = false;
                                                }

                                                cell = CreateTableUtil.createCell("开庭公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭公告详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(6);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案由",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseCause")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被告",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("defendant")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法官",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("judge")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("party")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("原告",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("plaintiff")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("法庭",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("courtroom")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("开庭时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                            }else if("FYGG".equals(String.valueOf(pageDataMap.get("dataType")))){//法院公告
                                                if(FYGG){
                                                    cell = CreateTableUtil.createCell("法院公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    FYGG = false;
                                                }

                                                cell = CreateTableUtil.createCell("法院公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法院公告详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(5);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("公告类型",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("announcementType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("开庭时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                            }else if("WDHMD".equals(String.valueOf(pageDataMap.get("dataType")))){//网贷黑名单
                                                if(WDHMD){
                                                    cell = CreateTableUtil.createCell("网贷黑名单",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    WDHMD = false;
                                                }
                                                cell = CreateTableUtil.createCell("网贷黑名单ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("网贷黑名单详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(8);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("本金/本息",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("principal")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("未还/罚息",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("penalty")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("已还金额",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("paid")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("信息更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("updateTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被执行人身份证/组织机构代码",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("identificationNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("相关当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("relatedParty")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("数据来源单位名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("sourceName")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("开庭时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                            }else if("AJLC".equals(String.valueOf(pageDataMap.get("dataType")))){//案件流程
                                                if(AJLC){
                                                    cell = CreateTableUtil.createCell("案件流程",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    AJLC = false;
                                                }
                                                cell = CreateTableUtil.createCell("案件流程ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案件流程详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(8);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNo")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案件类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("流程状态",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("status")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("立案时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("立案时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                            }
                                        }
                                        flag = false;
                                    }
                                }
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################车辆详情############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("车辆详情", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("4".equals(String.valueOf(itemMap.get("type")))) {//车辆详情
                        VehicleDetailsEnquiry vehicleDetailsEnquiry = JSONObject.parseObject(String.valueOf(itemMap.get("resultJson")), VehicleDetailsEnquiry.class);
                        if(vehicleDetailsEnquiry.getSuccess()){
                            com.rip.load.otherPojo.vehicleDetailsEnquiry.Data vehicleDetailsEnquiryData = vehicleDetailsEnquiry.getData();
                            cell = CreateTableUtil.createCell("查询状态",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            String status = "";
                            if ("EXIST".equals(vehicleDetailsEnquiryData.getStatus())){
                                status = "查询成功";
                            } else if("NO_DATA".equals(vehicleDetailsEnquiryData.getStatus())){
                                status = "无数据";
                            } else if("DIFFERENT".equals(vehicleDetailsEnquiryData.getStatus())){
                                status = "姓名不匹配";
                            } else if("NOT_MATCH".equals(vehicleDetailsEnquiryData.getStatus())){
                                status = "参数不正确";
                            }
                            cell = CreateTableUtil.createCell(status,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(7);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("车辆所有人",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getOwner(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(7);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("车牌号",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getLicensePlate(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            int type = Integer.valueOf(vehicleDetailsEnquiryData.getLicensePlateType());
                            String licensePlateType = "";
                            switch (type){
                                case 1:
                                    licensePlateType = "大型汽车";
                                    break;
                                case 2:
                                    licensePlateType = "小型汽车";
                                    break;
                                case 3:
                                    licensePlateType = "使馆汽车";
                                    break;
                                case 4:
                                    licensePlateType = "领馆汽车";
                                    break;
                                case 5:
                                    licensePlateType = "境外汽车";
                                    break;
                                case 6:
                                    licensePlateType = "外籍汽车";
                                    break;
                                case 7:
                                    licensePlateType = "普通摩托车";
                                    break;
                                case 8:
                                    licensePlateType = "轻便摩托车";
                                    break;
                                case 9:
                                    licensePlateType = "使馆摩托车";
                                    break;
                                case 10:
                                    licensePlateType = "领馆摩托车";
                                    break;
                                case 11:
                                    licensePlateType = "境外摩托车";
                                    break;
                                case 12:
                                    licensePlateType = "外籍摩托车";
                                    break;
                                case 13:
                                    licensePlateType = "低速车";
                                    break;
                                case 14:
                                    licensePlateType = "拖拉机";
                                    break;
                                case 15:
                                    licensePlateType = "挂车";
                                    break;
                                case 16:
                                    licensePlateType = "教练汽车";
                                    break;
                                case 17:
                                    licensePlateType = "教练摩托车";
                                    break;
                                case 20:
                                    licensePlateType = "临时入境汽车";
                                    break;
                                case 21:
                                    licensePlateType = "临时入境摩托车";
                                    break;
                                case 22:
                                    licensePlateType = "临时行驶车";
                                    break;
                                case 23:
                                    licensePlateType = "警用汽车";
                                    break;
                                case 24:
                                    licensePlateType = "大型汽车";
                                    break;
                            }
                            cell = CreateTableUtil.createCell("车牌类型",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(licensePlateType,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("车辆品牌名称",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getBrands(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("车辆型号",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getVehicleModel(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("车架号",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getVIN(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("发动机号",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getEngineNo(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("车辆类型",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            String vehicleType = "";
                            switch (vehicleDetailsEnquiryData.getVehicleType()){
                                case "K21":
                                    vehicleType = "中型普通客车";
                                    break;
                                case "K22":
                                    vehicleType = "中型双层客车";
                                    break;
                                case "K23":
                                    vehicleType = "中型卧铺客车";
                                    break;
                                case "K24":
                                    vehicleType = "中型铰接客车";
                                    break;
                                case "K25":
                                    vehicleType = "中型越野客车";
                                    break;
                                case "K26":
                                    vehicleType = "中型轿车";
                                    break;
                                case "K27":
                                    vehicleType = "中型专用客车";
                                    break;
                                case "K28":
                                    vehicleType = "中型专用校车";
                                    break;
                                case "K31":
                                    vehicleType = "小型普通客车";
                                    break;
                                case "K32":
                                    vehicleType = "小型越野客车";
                                    break;
                                case "K33":
                                    vehicleType = "小型轿车";
                                    break;
                                case "K34":
                                    vehicleType = "小型专用客车";
                                    break;
                                case "K38":
                                    vehicleType = "小型专用校车";
                                    break;
                                case "K41":
                                    vehicleType = "微型普通客车";
                                    break;
                                case "K42":
                                    vehicleType = "微型越野客车";
                                    break;
                                case "K43":
                                    vehicleType = "微型轿车";
                                    break;
                                default:
                                    vehicleType = "";
                                    break;

                            }
                            cell = CreateTableUtil.createCell(vehicleType,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("车身颜色",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getVehicleColor(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("使用性质",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getUsingCharacter(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("初次登记日期",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getRegisterDate(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("检验有效期止",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getValidTo(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("强制报废时间",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getScrapTo(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            String vehicleStatus = "";
                            switch (vehicleDetailsEnquiryData.getVehicleStatus()){
                                case "A": vehicleStatus = "正常";break;
                                case "B": vehicleStatus = "转出";break;
                                case "C": vehicleStatus = "被盗抢";break;
                                case "D": vehicleStatus = "停驶";break;
                                case "E": vehicleStatus = "注销";break;
                                case "G": vehicleStatus = "违法未处理";break;
                                case "H": vehicleStatus = "海关监管";break;
                                case "I": vehicleStatus = "事故未处理";break;
                                case "J": vehicleStatus = "嫌疑车";break;
                                case "K": vehicleStatus = "查封";break;
                                case "L": vehicleStatus = "暂扣";break;
                                case "M": vehicleStatus = "强制注销";break;
                                case "N": vehicleStatus = "事故逃逸";break;
                                case "O": vehicleStatus = "锁定";break;
                                case "P": vehicleStatus = "机动车达到报废标准，公告牌作废";break;
                                case "Q": vehicleStatus = "逾期未检验";break;
                            }
                            cell = CreateTableUtil.createCell("机动车状态",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleStatus,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("发动机型号",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getEngineType(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            String fuelType = "";
                            switch (vehicleDetailsEnquiryData.getFuelType()){
                                case "A":fuelType = "汽油";break;
                                case "B":fuelType = "柴油";break;
                                case "C":fuelType = "电驱动";break;
                                case "D":fuelType = "混合油";break;
                                case "E":fuelType = "天然气";break;
                                case "F":fuelType = "液化石油气";break;
                                case "L":fuelType = "甲醇";break;
                                case "M":fuelType = "乙醇";break;
                                case "N":fuelType = "太阳能";break;
                                case "O":fuelType = "混合动力";break;
                                case "Y":fuelType = "无";break;
                                case "Z":fuelType = "其他";break;
                            }
                            cell = CreateTableUtil.createCell("燃料种类",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(fuelType,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("排量(ml)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getEmissions(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("发动机最大功率(kw)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getMaximumPower(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("轴数",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getNumberAxles(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("轴距(mm)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getWheelBase(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("前轮距(mm)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getFrontTread(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("后轮距(mm)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getRearTread(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("总质量(kg)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getTotalMass(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("整备质量(kg)",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getVoidWeight(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("核定载客数",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getPassengersVerification(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("出厂日期",  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(vehicleDetailsEnquiryData.getProductionDate(),  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(null,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(null,  CreateTableUtil.textfont); //表格中添加的文字
                            cell.setColspan(3);
                            table.addCell(cell);

                            flag = false;
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);


                //备注
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
            // 关闭文档
            //document.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
