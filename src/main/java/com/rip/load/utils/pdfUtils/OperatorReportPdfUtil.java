package com.rip.load.utils.pdfUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 运营商信用报告
 * @Author: FYH
 * @Date: Created in 13:56 2019/5/21
 * @Modified:
 */
public class OperatorReportPdfUtil {
    private static String pieChartUrl = null;
    private static String barChartUrl = null;
    /**
     * 运营商信用报告
     * @throws IOException
     * @throws DocumentException
     */
    public static Boolean operatorReport(String json,String savePath) {
        try {
            Map<String, Object> jsonMap = JSON.parseObject(json);
            if (jsonMap.get("code") != null && Integer.valueOf(jsonMap.get("code").toString()) == 200) {
                Map<String, Object> resultMap = JSON.parseObject(jsonMap.get("result").toString());
                Map<String, Object> userCustomerMap = JSON.parseObject(resultMap.get("userCustomer").toString());
                JSONArray itemListArray = JSONArray.parseArray(resultMap.get("itemList").toString());//用户详情
                for (Object items : itemListArray) {
                    Map<String, Object> itemsMap = JSON.parseObject(String.valueOf(items));
                    if ("10".equals(String.valueOf(itemsMap.get("type")))) {//运营商信用报告
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
                        Map<String,Object> reportMap = JSON.parseObject(dataMap.get("report").toString());//数据源
                        Map<String,Object> relationInfoMap = JSON.parseObject(dataMap.get("relationInfo").toString());//关联信息
                        JSONArray basicInfoCheckArray = JSON.parseArray(dataMap.get("basicInfoCheck").toString());//基本信息核验
                        JSONArray riskListCheckArray = JSON.parseArray(dataMap.get("riskListCheck").toString()); //风险清单检查
                        JSONArray overdueLoanCheckArray = JSON.parseArray(dataMap.get("overdueLoanCheck").toString()); //信贷逾期检查
                        JSONArray multiLendCheckArray = JSON.parseArray(dataMap.get("multiLendCheck").toString()); //多头借贷检查
                        JSONArray riskCallCheckArray = JSON.parseArray(dataMap.get("riskCallCheck").toString()); //风险通话检测
                        Map<String,Object> personasMap = JSON.parseObject(dataMap.get("personas").toString());//用户画像
                        Map<String,Object> riskProfileMap = JSON.parseObject(personasMap.get("riskProfile").toString());//用户画像——风险概况
                        Map<String,Object> socialContactProfileMap = JSON.parseObject(personasMap.get("socialContactProfile").toString());//用户画像——社交概况
                        Map<String,Object> callProfileMap = JSON.parseObject(personasMap.get("callProfile").toString());//用户画像——通话概况
                        Map<String,Object> consumptionProfileMap = JSON.parseObject(personasMap.get("consumptionProfile").toString());//用户画像——消费概况
                        Map<String,Object> callAnalysisMap = JSON.parseObject(dataMap.get("callAnalysis").toString());//通话概况
                        JSONArray activeCallAnalysisArray = JSON.parseArray(dataMap.get("activeCallAnalysis").toString());//活跃情况
                        Map<String,Object> silenceAnalysisMap = JSON.parseObject(dataMap.get("silenceAnalysis").toString());//静默情况
                        JSONArray callDurationAnalysisArray = JSON.parseArray(dataMap.get("callDurationAnalysis").toString()); //通话时间段分析
                        JSONArray consumptionAnalysisArray = JSON.parseArray(dataMap.get("consumptionAnalysis").toString()); //消费能力
                        JSONArray tripAnalysisArray = JSON.parseArray(dataMap.get("tripAnalysis").toString()); //出行信息
                        JSONArray socialContactAnalysisArray = JSON.parseArray(dataMap.get("socialContactAnalysis").toString()); //社交关系概况
                        JSONArray callAreaAnalysisArray = JSON.parseArray(dataMap.get("callAreaAnalysis").toString()); //通话区域分析
                        JSONArray contactAnalysisArray = JSON.parseArray(dataMap.get("contactAnalysis").toString()); //通话联系人分析

                        //一、获取认证验证的数据
                        //0身份证号是否命中风险清单
                        String riskListOfIdentityCard = "未命中";
                        Font riskListOfIdentityCardFont = CreateTableUtil.textGreenFont;//设置字体颜色（未命中绿色，命中红色）;
                        //1.是否实名认证
                        String realNameAuthentication = ""; //是否实名认证
                        Font realNameAuthenticationFont = CreateTableUtil.textGreenFont;//设置字体颜色（实名认证绿色，未实名认证红色）
                        //2.手机号是否命中风险清单
                        String mobileNumberRiskList = "未命中";
                        Font mobileNumberRiskListFont = CreateTableUtil.textGreenFont;//设置字体颜色（未命中绿色，命中红色）
                        //3.姓名与运营商数据是否匹配
                        String nameAndOperatorMatching = "";
                        Font nameAndOperatorMatchingFont = CreateTableUtil.textGreenFont;//设置字体颜色（匹配绿色，不匹配红色）
                        //4.身份证号与运营商数据是否匹配
                        String idcardAndOperatorMatching = "";
                        Font idcardAndOperatorMatchingFont = CreateTableUtil.textGreenFont;//设置字体颜色（匹配绿色，不匹配红色）
                        for(Object basicInfoCheck:basicInfoCheckArray){//遍历基本信息检查
                            Map<String,Object> basicInfoCheckMap = JSON.parseObject(basicInfoCheck.toString());
                            if("idcard_check".equals(basicInfoCheckMap.get("item"))){//身份证校验

                            }else if("mobile_check".equals(basicInfoCheckMap.get("item").toString())){//手机号实名制验证
                                realNameAuthentication = basicInfoCheckMap.get("resultDesc").toString();
                                if("0".equals(basicInfoCheckMap.get("result"))){//0：未实名认证，显示红色
                                    realNameAuthenticationFont = CreateTableUtil.textRedFont;
                                }
                            }else if ("idcard_match".equals(basicInfoCheckMap.get("item"))){//身份证与运营商数据匹配
                                idcardAndOperatorMatching = basicInfoCheckMap.get("resultDesc").toString();
                                if("0".equals(basicInfoCheckMap.get("result"))){
                                    idcardAndOperatorMatchingFont = CreateTableUtil.textRedFont;
                                }else if("2".equals(basicInfoCheckMap.get("result")) || "3".equals(basicInfoCheckMap.get("result"))){
                                    idcardAndOperatorMatchingFont = CreateTableUtil.textOrangeFont;
                                }

                            }else if ("name_match".equals(basicInfoCheckMap.get("item"))){//姓名与运营商数据匹配
                                nameAndOperatorMatching = basicInfoCheckMap.get("resultDesc").toString();
                                if("0".equals(basicInfoCheckMap.get("result"))){
                                    nameAndOperatorMatchingFont = CreateTableUtil.textRedFont;
                                }else if("2".equals(basicInfoCheckMap.get("result")) || "3".equals(basicInfoCheckMap.get("result"))){
                                    nameAndOperatorMatchingFont = CreateTableUtil.textOrangeFont;
                                }
                            }
                        }
                        for(Object riskListCheck:riskListCheckArray){
                            Map<String,Object> riskListCheckMap = JSON.parseObject(riskListCheck.toString());
                            if("1".equals(riskListCheckMap.get("result"))){
                                if(riskListCheckMap.get("item").toString().startsWith("idcard")){
                                    riskListOfIdentityCard = "命中";
                                    riskListOfIdentityCardFont = CreateTableUtil.textRedFont;
                                }else if(riskListCheckMap.get("item").toString().startsWith("mobile")){
                                    mobileNumberRiskList = "命中";
                                    mobileNumberRiskListFont = CreateTableUtil.textRedFont;//设置字体颜色（实名认证绿色，未实名认证红色）
                                }
                            }
                        }

                        PdfPTable table = CreateTableUtil.createTable(1);
                        Image img = null;
                        try{
                            img = Image.getInstance("../img/operator.jpg");//图片的地址
                        }catch (Exception e){
                        }
                        if(img == null)
                            img = Image.getInstance("/usr/local/tomcatfangdi-8088/webapps/img/operator.jpg");//图片的地址

                        img.scaleAbsolute(CreateTableUtil.mmTopx(200),CreateTableUtil.mmTopx(297));
                        PdfPCell photoCell = new PdfPCell(img);
                        photoCell.setBorder(0);
                        photoCell.setHorizontalAlignment(1);
                        table.addCell(photoCell);
                        document.add(table);

                        // 向文档中添加内容
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

                        //################################################大标题标题############################################################
                        paragraph = new Paragraph("运营商信用报告",CreateTableUtil.headfontfirst);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));

                        //   \t不起作用,没办法只能用n多的空格代替
                        String table_up = "报告编号 : "+reportMap.get("reportNo")+"                                            " +
                                "数据来源 : "+reportMap.get("dataSource").toString()+"                                         "+
                                "获取时间 : "+reportMap.get("reportTime").toString();
                        paragraph = new Paragraph(table_up,CreateTableUtil.textfont);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));

                        //################################################基本信息############################################################
                        table = CreateTableUtil.createTable(6);
                        float[] bisicInfocolumnWidths = { 1f, 1f, 1f, 1f, 1f, 1f};
                        table.setWidths(bisicInfocolumnWidths);
                        //######第一行内容######
                        PdfPCell cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("姓名:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        String name = String.valueOf(basicInfoMap.get("name"));
                        if(!StringUtils.isEmpty(name)){
                            name = ProtectingPrivacyUtil.nameEncrypt(name);
                        }
                        cell = CreateTableUtil.createCell(name, CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("性别:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("gender").toString(), CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("年龄:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("age").toString(), CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("地址:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("nativeAddress").toString(), CreateTableUtil.textfont);
                        cell.setColspan(5);
                        table.addCell(cell);
                        document.add(table);
                        table = CreateTableUtil.createTable(18);

                        //######第三行内容######
                        cell = CreateTableUtil.createCell("身份证号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(3);//跨列
                        cell.setRowspan(2);//跨行
                        table.addCell(cell);
                        String identityNo = String.valueOf(basicInfoMap.get("identityNo"));
                        if(!StringUtils.isEmpty(identityNo)){
                            identityNo = ProtectingPrivacyUtil.idEncrypt(String.valueOf(basicInfoMap.get("identityNo")));
                        }
                        cell = CreateTableUtil.createCell(identityNo, CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(15);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("身份证号是否命中风险清单", CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskListOfIdentityCard, riskListOfIdentityCardFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(11);
                        table.addCell(cell);

                        //######第四行内容######
                        cell = CreateTableUtil.createCell("手机号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(3);
                        cell.setRowspan(4);//跨行
                        table.addCell(cell);
                        String phone = String.valueOf(basicInfoMap.get("mobile"));
                        if(!StringUtils.isEmpty(phone)){
                            phone = ProtectingPrivacyUtil.mobileEncrypt(phone);
                        }
                        cell = CreateTableUtil.createCell(phone, CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setColspan(2);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell(realNameAuthentication,realNameAuthenticationFont);
                        cell.setBorder(0);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("入网时间 : "+basicInfoMap.get("regTime").toString(),CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(9);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("手机号是否命中风险清单",CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(mobileNumberRiskList,mobileNumberRiskListFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(11);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("姓名与运营商数据是否匹配",CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(nameAndOperatorMatching,nameAndOperatorMatchingFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(11);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("身份证号与运营商数据是否匹配",CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(5);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(idcardAndOperatorMatching,idcardAndOperatorMatchingFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(10);
                        table.addCell(cell);
                        document.add(table);

                        //################################################关联信息############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容######
                        cell = CreateTableUtil.createCell("关联信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("手机关联的身份证", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        String idcard = relationInfoMap.get("identiyNos").toString();
                        String mobileAndIdcard = "无相关数据";
                        if(!"[]".equals(idcard)){
                            String[] identiyNos = idcard.substring(idcard.indexOf("[")+1,idcard.lastIndexOf("]")).split(",");
                            StringBuilder stb = new StringBuilder();
                            for(String s:identiyNos){
                                stb.append(s);
                                stb.append("        ");
                            }
                            mobileAndIdcard = stb.toString();
                        }
                        cell = CreateTableUtil.createCell(mobileAndIdcard, CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(5);
                        table.addCell(cell);
                        //######第三行内容######
                        cell = CreateTableUtil.createCell("身份证关联的手机", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        String mobile = relationInfoMap.get("mobiles").toString();
                        String idcardAndMobile = "无相关数据";
                        if(!"[]".equals(mobile)){
                            String[] mobiles = idcard.substring(mobile.indexOf("[")+1,mobile.lastIndexOf("]")).split(",");
                            StringBuilder stb = new StringBuilder();
                            for(String s:mobiles){
                                stb.append(s);
                                stb.append("        ");
                            }
                            idcardAndMobile = stb.toString();
                        }
                        cell = CreateTableUtil.createCell(idcardAndMobile, CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(5);
                        table.addCell(cell);
                        //######第四行内容######
                        cell = CreateTableUtil.createCell("身份证关联的地址", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        String address = relationInfoMap.get("mobiles").toString();
                        String idcardAndAddress = "无相关数据";
                        if(!"[]".equals(mobile)){
                            String[] addresss = idcard.substring(address.indexOf("[")+1,address.lastIndexOf("]")).split(",");
                            StringBuilder stb = new StringBuilder();
                            for(String s:addresss){
                                stb.append(s);
                                stb.append("        ");
                            }
                            idcardAndAddress = stb.toString();
                        }
                        cell = CreateTableUtil.createCell(idcardAndAddress, CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(5);
                        table.addCell(cell);
                        document.add(table);

                        //################################################用户画像############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("用户画像", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容（评分）######
                        if(dataMap.get("scoreAnalysis") != null){
                            Map<String,Object> scoreAnalysisMap = JSON.parseObject(dataMap.get("scoreAnalysis").toString());
                            JSONArray deductionDetailsArray = JSON.parseArray(scoreAnalysisMap.get("deductionDetails").toString());


                            cell = CreateTableUtil.createCell("评分", CreateTableUtil.textfont);
                            cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                            cell.setRowspan(deductionDetailsArray.size()+1);//跨行
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(scoreAnalysisMap.get("score").toString()+"分", CreateTableUtil.textfont);
                            cell.setColspan(2);
                            cell.setBorderWidthRight(0);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("综合评估个人信用的分值", CreateTableUtil.textkeyfont);
                            cell.setColspan(3);
                            cell.setBorderWidthLeft(0);
                            table.addCell(cell);

                            if(deductionDetailsArray.size()>0){
                                cell = CreateTableUtil.createCell("扣分明细", CreateTableUtil.textfont);
                                cell.setRowspan(deductionDetailsArray.size());//跨行
                                cell.setBorderWidthRight(0);
                                cell.setBorderWidthTop(0);
                                table.addCell(cell);
                                int i = 1;
                                for(Object deductionDetails:deductionDetailsArray){
                                    Map<String,Object> deductionDetailMap = JSON.parseObject(deductionDetails.toString());
                                    cell = CreateTableUtil.createCell(deductionDetailMap.get("deduction").toString()+"分",CreateTableUtil.textRedFont);
                                    cell.setBorder(0);
                                    if(i == deductionDetailsArray.size()) {
                                        cell.setBorderWidthBottom(0.1f);
                                    }
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(deductionDetailMap.get("desc").toString(),CreateTableUtil.textkeyfont);
                                    cell.setColspan(3);
                                    cell.setBorder(0);
                                    cell.setBorderWidthRight(0.1f);
                                    if(i == deductionDetailsArray.size()) {
                                        cell.setBorderWidthBottom(0.1f);
                                    }
                                    table.addCell(cell);
                                    i++;
                                }
                            }
                        }
                        //######第三行内容（风险情况）######
                        cell = CreateTableUtil.createCell("风险情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(4);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("风险清单", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("riskListCnt")+"次", riskListCheckArray.size()==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本人身份证号、手机号命中风险清单总次数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("信贷逾期", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("overdueLoanCnt")+"次", Integer.valueOf(riskProfileMap.get("overdueLoanCnt").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本人身份证或手机号命中信贷逾期的情况", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("多头借贷", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("multiLendCnt")+"次", Integer.valueOf(riskProfileMap.get("multiLendCnt").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本人身份证或手机号命中多平台借贷申请情况", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("风险通话", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("riskCallCnt")+"次", Integer.valueOf(riskProfileMap.get("riskCallCnt").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本手机号与网贷、110 等电话联系次数统计", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        //######第三行内容（社交情况）######
                        cell = CreateTableUtil.createCell("社交情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(4);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("最常联系地区", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("freContactArea").toString(), CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机通话次数按区域分前3 位\n", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("联系人号码总数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("contactNumCnt").toString()+"个", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机通话的号码总数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("互通号码总数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("interflowContactCnt").toString()+"个", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机相互通过话的号码总数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("联系人风险名单总数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("contactRishCnt").toString()+"个", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机号通话的号码命中风险名单的总数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        //######第三行内容（通话情况）######
                        cell = CreateTableUtil.createCell("通话情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(6);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均通话次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("avgCallCnt").toString()+"次", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内总通话次数按日平均", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均通话时长", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("avgCallTime").toString()+"分钟", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内总通话时长按日平均", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("手机静默次数", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("silenceCnt").toString()+"次", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内超过24小时持续无通话无短信的总次数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("( 重点关注：手机如果经常关机或不在服务区，有养号可能)", CreateTableUtil.textRedFont);
                        cell.setColspan(3);
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("夜间通话次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("nightCallCnt")+"次", CreateTableUtil.textfont);
                        cell.setBorder(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("统计23：00-5：30 的通话次数和平均时长（按次平均）", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setRowspan(2);
                        cell.setColspan(3);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("平均时长", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("nightCallTime")+"分钟", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        //######第四行内容（消费情况）######
                        cell = CreateTableUtil.createCell("消费情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("月均消费", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);//跨行
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(consumptionProfileMap.get("avgFeeMonth")+"元", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);//跨行
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("6个内总消费按月平均", CreateTableUtil.textfont);
                        cell.setColspan(3);
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("(重点关注：有小号，副号可能)", CreateTableUtil.textRedFont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        document.add(table);

                        //################################################风险清单检测############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("风险清单检测", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("      命中次数", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        if(riskListCheckArray.size()>0){
                            for(Object riskListCheck:riskListCheckArray){
                                Map<String,Object> riskListCheckMap = JSON.parseObject(riskListCheck.toString());
                                cell = CreateTableUtil.createCell(riskListCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                                cell.setColspan(2);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("              "+riskListCheckMap.get("result").toString(), Integer.valueOf(riskListCheckMap.get("result").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                                cell.setColspan(4);
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(6);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);


                        //################################################信贷逾期############################################################
                        table = CreateTableUtil.createTable(30);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("信贷逾期", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(30); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        //######第一行内容######
                        cell = CreateTableUtil.createCell("类别", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(10);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(20);
                        table.addCell(cell);

                        //######第三行内容######
                        if(overdueLoanCheckArray.size()>0){
                            for(Object overdueLoanCheck:overdueLoanCheckArray){
                                Map<String,Object> overdueLoanCheckMap = JSON.parseObject(overdueLoanCheck.toString());
                                JSONArray detailsArray = JSON.parseArray(overdueLoanCheckMap.get("details").toString()); //信贷逾期详情
                                int i = 1;
                                if(detailsArray.size() > 0){
                                    cell = CreateTableUtil.createCell(overdueLoanCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                    cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                                    cell.setRowspan(detailsArray.size());
                                    cell.setColspan(10);
                                    table.addCell(cell);
                                    for(Object details:detailsArray){
                                        //System.out.println(detailsArray.size());
                                        Map<String,Object> detailsMap = JSON.parseObject(details.toString());

                                        cell = CreateTableUtil.createCell("逾期金额 :", CreateTableUtil.textfont);
                                        cell.setBorderWidthRight(0);
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("overdueAmt")+"元", CreateTableUtil.textRedFont);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        cell.setColspan(4);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("逾期天数 :", CreateTableUtil.textfont);
                                        cell.setColspan(3);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("overdueDays")+"天", CreateTableUtil.textRedFont);
                                        cell.setColspan(4);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("逾期时间 :", CreateTableUtil.textfont);
                                        cell.setColspan(3);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("overdueTime").toString(), CreateTableUtil.textRedFont);
                                        cell.setColspan(3);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                    }
                                }
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(15);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);

                        //################################################多头借贷############################################################
                        table = CreateTableUtil.createTable(3);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("多头借贷", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(3); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        //######第二行内容######
                        cell = CreateTableUtil.createCell("类别", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(2);
                        table.addCell(cell);

                        //######第三行内容######
                        if(multiLendCheckArray.size()>0){
                            for(Object multiLendCheck:multiLendCheckArray){
                                Map<String,Object> multiLendCheckMap = JSON.parseObject(multiLendCheck.toString());
                                JSONArray detailsArray = JSON.parseArray(multiLendCheckMap.get("details").toString());
                                if(detailsArray.size()>0){
                                    cell = CreateTableUtil.createCell(multiLendCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                    cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    for(Object details:detailsArray){
                                        Map<String,Object> detailsMap = JSON.parseObject(details.toString());
                                        cell = CreateTableUtil.createCell(detailsMap.get("lendType").toString(), CreateTableUtil.textfont);
                                        cell.setBorderWidthRight(0);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("lendCnt")+"次", CreateTableUtil.textRedFont);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                    }
                                }
                            }
                        }
                        document.add(table);

                        //################################################风险通话检测############################################################
                        table = CreateTableUtil.createTable(9);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("风险通话检测", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(9); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("检查项", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("命中描述", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(2);
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("命中次数", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("时长(s)", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(4);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话标记", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话类型", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话时长(s)", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        if(riskCallCheckArray.size()>0){
                            for(Object riskCallCheck:riskCallCheckArray){
                                Map<String,Object> riskCallCheckMap = JSON.parseObject(riskCallCheck.toString());
                                JSONArray detailsArray = JSON.parseArray(riskCallCheckMap.get("details").toString());
                                if(detailsArray.size()>0){
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("hitDesc").toString(), CreateTableUtil.textfont);
                                    cell.setColspan(2);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("cnt").toString(), CreateTableUtil.textfont);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("duration").toString(), CreateTableUtil.textfont);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    for(Object details:detailsArray){
                                        Map<String,Object> detailsMap = JSON.parseObject(details.toString());
                                        cell = CreateTableUtil.createCell(detailsMap.get("callTag").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("callType").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("callCnt").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("callTime").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                    }
                                }
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(9);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }

                        document.add(table);

                        //################################################通话概况############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("通话概况", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均通话次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均通话时长", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均主叫次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均主叫时长", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallCnt")+"次", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallTime")+"分钟", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallingCnt")+"次", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallingTime")+"分钟", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均被叫次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均被叫时长", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本地通话占比", CreateTableUtil.textfont);
                        cell.setBorderWidthBottom(0);
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCalledCnt")+"次", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCalledTime")+"分钟", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("locCallPct").toString(), CreateTableUtil.textGreenFont);
                        cell.setColspan(2);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        document.add(table);
                        //################################################活跃情况############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("活跃情况", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近1月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近3月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近4月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("月均", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(activeCallAnalysisArray.size()>0){
                            for(Object activeCallAnalysis:activeCallAnalysisArray){
                                Map<String,Object> activeCallAnalysisMap = JSON.parseObject(activeCallAnalysis.toString());

                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2); //合并列
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("lately1m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("lately3m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("lately6m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("avgMonth").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(6);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }

                        document.add(table);
                        //################################################静默情况（近六个月）############################################################
                        table = CreateTableUtil.createTable(72);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("静默情况(近六个月)", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(14); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("(重点关注：离开长居地时，手机不在服务区或关机)", CreateTableUtil.headRedfont); //表格中添加的文字
                        cell.setColspan(58); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("总静默次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(7);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("总静默时长(小时) ", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(10);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最长一次静默开始时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最长一次静默时长(小时) ", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次静默开始时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(13);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次静默时长(小时)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);

                        //######第三行内容######
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("silenceCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(7);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("silenceTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(10);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("longestSilenceStart").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("longestSilenceTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("lastSilenceStart").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(13);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("lastSilenceTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);

                        document.add(table);
                        //################################################通话时间分析（近6 个月）############################################################
                        table = CreateTableUtil.createTable(40);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("通话时间分析(近6个月)", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(40); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        document.add(table);
                        //插入饼图
                        table = CreateTableUtil.createTable(1);
                        DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
                        if(callDurationAnalysisArray.size()>0) {
                            for (Object callDurationAnalysis : callDurationAnalysisArray) {
                                Map<String,Object> callDurationAnalysisMap = JSON.parseObject(callDurationAnalysis.toString());
                                //创建统计图
                                dpd.setValue(callDurationAnalysisMap.get("desc").toString(), Integer.valueOf(callDurationAnalysisMap.get("callCnt").toString()));  //输入数据
                            }
                            pieChartUrl = StatisticalChartUtil.createStatistrical(dpd,"通话时间分析（近六个月）");
                            if(pieChartUrl != null){
                                img = Image.getInstance(pieChartUrl);//图片的地址
                                img.scaleAbsolute(500,300);
                                photoCell = new PdfPCell(img);
                                photoCell.setHorizontalAlignment(1);
                                photoCell.setRowspan(20);
                                photoCell.setBorder(0);
                                photoCell.setBorderWidthRight(0.1f);
                                photoCell.setBorderWidthLeft(0.1f);
                                table.addCell(photoCell);
                                document.add(table);
                            }
                        }

                        table = CreateTableUtil.createTable(40);
                        cell = CreateTableUtil.createCell("时间段", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话号码数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最常联系号码及联系次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(8);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("平均通话时长(秒)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(3);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫时长(秒)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(5);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(3);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫时长(秒)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(5);
                        table.addCell(cell);

                        if(callDurationAnalysisArray.size()>0){
                            for(Object callDurationAnalysis:callDurationAnalysisArray){
                                Map<String,Object> callDurationAnalysisMap = JSON.parseObject(callDurationAnalysis.toString());

                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //描述
                                cell.setColspan(4);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callCnt").toString(), CreateTableUtil.textfont); //通话次数
                                cell.setColspan(4);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callNumCnt").toString(), CreateTableUtil.textfont); //通话号码数
                                cell.setColspan(4);
                                table.addCell(cell);
                                if(StringUtils.isEmpty(callDurationAnalysisMap.get("freqContactNum").toString())){
                                    cell = CreateTableUtil.createCell("----", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setBorderWidthRight(0);
                                    cell.setColspan(5);
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(3);
                                    cell.setBorderWidthLeft(0);
                                    table.addCell(cell);
                                }else {
                                    cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("freqContactNum")+" :", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(5);
                                    cell.setBorderWidthRight(0);
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("freqContactNumCnt")+"次", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(3);
                                    cell.setBorderWidthLeft(0);
                                    table.addCell(cell);
                                }
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("avgCallTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(4);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callingCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callingTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("calledCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("calledTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(40);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################消费能力############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("消费能力", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近1月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近3月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近6月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("平均", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(consumptionAnalysisArray.size()>0){
                            for(Object consumptionAnalysis:consumptionAnalysisArray){
                                Map<String,Object> consumptionAnalysisMap = JSON.parseObject(consumptionAnalysis.toString());
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("lately1m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("lately3m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("lately6m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("avgMonth").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(6);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);

                        //################################################出行信息############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("出行信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("出发时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("回程时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("出发地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("目的地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(tripAnalysisArray.size()>0){
                            for(Object tripAnalysis:tripAnalysisArray){
                                Map<String,Object> tripAnalysisMap = JSON.parseObject(tripAnalysis.toString());
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("departureDate").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("returnDate").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("departurePlace").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("destinationPlace").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(4);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################社交关系############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("社交关系", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容描述", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(socialContactAnalysisArray.size()>0){
                            for(Object socialContactAnalysis:socialContactAnalysisArray){
                                Map<String,Object> socialContactAnalysisMap = JSON.parseObject(socialContactAnalysis.toString());
                                cell = CreateTableUtil.createCell(socialContactAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(socialContactAnalysisMap.get("content").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(socialContactAnalysisMap.get("contentDesc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(4);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################通话区域分析############################################################
                        table = CreateTableUtil.createTable(8);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("通话区域分析", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        document.add(table);

                        table = CreateTableUtil.createTable(1);
                        //创建条形统计图
                        DefaultCategoryDataset dataset=new DefaultCategoryDataset();//创建条形统计图
                        //添加数据
                        if(callAreaAnalysisArray.size()>0) {
                            for (Object callAreaAnalysis : callAreaAnalysisArray) {
                                Map<String, Object> callAreaAnalysisMap = JSON.parseObject(callAreaAnalysis.toString());
                                dataset.addValue(Integer.valueOf(callAreaAnalysisMap.get("callCnt").toString()),"地区",callAreaAnalysisMap.get("attribution").toString());
                            }
                            barChartUrl = StatisticalChartUtil.createBarGraph(dataset,"通话区域分析","","次数");
                            if(barChartUrl != null){
                                img = Image.getInstance(barChartUrl);//图片的地址
                                img.scaleAbsolute(500,300);
                                photoCell = new PdfPCell(img);
                                photoCell.setHorizontalAlignment(1);
                                photoCell.setRowspan(20);
                                photoCell.setBorder(0);
                                photoCell.setBorderWidthRight(0.1f);
                                photoCell.setBorderWidthLeft(0.1f);
                                table.addCell(photoCell);
                                document.add(table);
                            }
                        }
                        table = CreateTableUtil.createTable(8);
                        cell = CreateTableUtil.createCell("通话地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话号码数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(callAreaAnalysisArray.size()>0){
                            for(Object callAreaAnalysis:callAreaAnalysisArray){
                                Map<String,Object> callAreaAnalysisMap = JSON.parseObject(callAreaAnalysis.toString());
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("attribution").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callNumCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callingCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callingTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("calledCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("calledTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(8);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################通话联系人分析############################################################
                        table = CreateTableUtil.createTable(15);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        cell = CreateTableUtil.createCell("通话联系人分析", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(15); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("号码", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("互联网标识", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("风险名单", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("归属地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次通话时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(3);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次通话时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        if(contactAnalysisArray.size()>0){
                            for(Object contactAnalysis:contactAnalysisArray){
                                Map<String,Object> contactAnalysisMap = JSON.parseObject(contactAnalysis.toString());
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callNum").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callTag").toString(), CreateTableUtil.textfont); //互联网标识
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("isHitRiskList").toString(), CreateTableUtil.textfont); //风险名单
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("attribution").toString(), CreateTableUtil.textfont); //归属地
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callCnt").toString(), CreateTableUtil.textfont); //通话次数
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callTime").toString(), CreateTableUtil.textfont); //通话时长(s)
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callingCnt").toString(), CreateTableUtil.textfont); //主叫次数
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callingTime").toString(), CreateTableUtil.textfont); //主叫时长(s)
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("calledCnt").toString(), CreateTableUtil.textfont); //被叫次数
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("calledTime").toString(), CreateTableUtil.textfont); //被叫时长(s)
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("lastStart").toString(), CreateTableUtil.textfont); //最近一次通话时间
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("lastTime").toString(), CreateTableUtil.textfont); //最近一次通话时长(s)
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(14);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);

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
            // 关闭文档
            //document.close();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(pieChartUrl != null){
                StatisticalChartUtil.deletePhoto(pieChartUrl);
            }
            if(barChartUrl != null){
                StatisticalChartUtil.deletePhoto(barChartUrl);
            }
        }
    }
}
