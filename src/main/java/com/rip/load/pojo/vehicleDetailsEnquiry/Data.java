package com.rip.load.pojo.vehicleDetailsEnquiry;

public class Data {

    /**
     *车牌号
     */
    private String licensePlate;

    /**
     *号牌类型 （详见“号牌种类数据字典” ）
     */
    private String licensePlateType;

    /**
     *车辆品牌名称
     */
    private String brands;

    /**
     *车辆型号
     */
    private String vehicleModel;

    /**
     *车架号
     */
    private String VIN;

    /**
     *发动机号
     */
    private String engineNo;

    /**
     *车辆类型（详见“车辆类型数据字典” ）
     */
    private String vehicleType;

    /**
     *车身颜色
     */
    private String vehicleColor;

    /**
     *使用性质
     */
    private String usingCharacter;

    /**
     *所有人
     */
    private String owner;

    /**
     *初次登记日期
     */
    private String registerDate;

    /**
     *检验有效期止
     */
    private String validTo;

    /**
     *强制报废时间
     */
    private String scrapTo;
    /**
     *机动车状态（详见“机动车状态数据字典”
     */
    private String vehicleStatus;
    /**
     *发动机型号
     */
    private String engineType;
    /**
     *燃料种类（详见“燃料种类数据字典”）
     */
    private String fuelType;
    /**
     *排量（ml）
     */
    private String emissions;
    /**
     *发动机最大功率（kw）
     */
    private String maximumPower;
    /**
     *轴数
     */
    private String numberAxles;
    /**
     *轴距（mm）
     */
    private String wheelBase;
    /**
     *前轮距（mm）
     */
    private String frontTread;
    /**
     *后轮距（mm）
     */
    private String rearTread;
    /**
     *总质量（kg）
     */
    private String totalMass;
    /**
     *整备质量（kg）
     */
    private String voidWeight;
    /**
     *核定载客数
     */
    private String passengersVerification;
    /**
     *出厂日期
     */
    private String productionDate;
    /**
     *EXIST（查询成功） 、 NO_DATA（无数
     * 据） 、 DIFFERENT（姓名不匹配） 、
     * NOT_MATCH（参数不正确）
     */
    private String status;


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlateType() {
        return licensePlateType;
    }

    public void setLicensePlateType(String licensePlateType) {
        this.licensePlateType = licensePlateType;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getUsingCharacter() {
        return usingCharacter;
    }

    public void setUsingCharacter(String usingCharacter) {
        this.usingCharacter = usingCharacter;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getScrapTo() {
        return scrapTo;
    }

    public void setScrapTo(String scrapTo) {
        this.scrapTo = scrapTo;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEmissions() {
        return emissions;
    }

    public void setEmissions(String emissions) {
        this.emissions = emissions;
    }

    public String getMaximumPower() {
        return maximumPower;
    }

    public void setMaximumPower(String maximumPower) {
        this.maximumPower = maximumPower;
    }

    public String getNumberAxles() {
        return numberAxles;
    }

    public void setNumberAxles(String numberAxles) {
        this.numberAxles = numberAxles;
    }

    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    public String getFrontTread() {
        return frontTread;
    }

    public void setFrontTread(String frontTread) {
        this.frontTread = frontTread;
    }

    public String getRearTread() {
        return rearTread;
    }

    public void setRearTread(String rearTread) {
        this.rearTread = rearTread;
    }

    public String getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(String totalMass) {
        this.totalMass = totalMass;
    }

    public String getVoidWeight() {
        return voidWeight;
    }

    public void setVoidWeight(String voidWeight) {
        this.voidWeight = voidWeight;
    }

    public String getPassengersVerification() {
        return passengersVerification;
    }

    public void setPassengersVerification(String passengersVerification) {
        this.passengersVerification = passengersVerification;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
