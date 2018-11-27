package com.grpc.service.grpcservice.web;

import com.grpc.service.grpcservice.base.GrpcBase;
import com.grpc.service.grpcservice.base.User;
import com.grpc.service.grpcservice.model.request.GrpcRequest;
import com.grpc.service.grpcservice.model.request.PaymentRequest;
import com.grpc.service.grpcservice.model.response.CardInfo;
import com.grpc.service.grpcservice.model.response.OrderInfo;
import com.grpc.service.grpcservice.model.response.SIMCardPortrait;
import com.grpc.service.grpcservice.model.response.StringEntry;
import com.grpc.service.util.BeanUtil;
import onelink.api.oneservice.account.service.AccountInfoParamsRpc;
import onelink.api.oneservice.account.service.QueryAccountInfoReply;
import onelink.api.oneservice.account.service.QueryAccountInfoRequest;
import onelink.api.oneservice.bill.service.*;
import onelink.api.oneservice.cm.service.*;
import onelink.api.oneservice.common.Page;
import onelink.api.oneservice.order.service.*;
import onelink.api.oneservice.riskcontrol.service.SIMCardPortraitParamsRpc;
import onelink.api.oneservice.riskcontrol.service.SIMCardPortraitReply;
import onelink.api.oneservice.riskcontrol.service.SIMCardPortraitRequest;
import onelink.api.oneservice.riskcontrol.service.SIMCardPortraitRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lz
 * 2018/10/24 10:30
 */
@RestController
@RequestMapping("/grpc")
public class NewGrpcController extends GrpcBase{

    private static final Logger log = LoggerFactory.getLogger(NewGrpcController.class);

    @RequestMapping("/queryAccount")
    public void queryAccount(String phone){
        QueryAccountInfoRequest.Builder request = QueryAccountInfoRequest.newBuilder();
        Page.Builder page = Page.newBuilder();
        page.setTotal(12);
        page.setIndex(1);
        page.setSize(0);
        page.setChannel("WEB");

        AccountInfoParamsRpc.Builder params = AccountInfoParamsRpc.newBuilder();
        params.setEntityType("S");
        params.setEntityId(phone);
        request.setPage(page);
        request.setAccountInfoParams(params);

        QueryAccountInfoReply queryAccountInfoReply = accountServiceBlockingStub.queryAccountInfo(request.build());
        for (int i = 0; i < queryAccountInfoReply.getAccountListCount(); i++){
            log.info("queryAccountInfoReply--->{}", queryAccountInfoReply.getAccountList(i).getAccountName());
        }
    }

    @RequestMapping("/queryOfferings")
    public User queryOfferings(@RequestBody GrpcRequest grpcRequest){

        OfferingsParamsRpc.Builder offeringsParams = OfferingsParamsRpc.newBuilder();
        offeringsParams.setEntityId(grpcRequest.getEntityId());
        offeringsParams.setEntityType(grpcRequest.getEntityType());
        offeringsParams.setBeId(grpcRequest.getBeId());
        offeringsParams.setCustId(grpcRequest.getCustId());

        Page page = Page.newBuilder().setTotal(1000).setIndex(1).setSize(10).setChannel("WEB").build();

        QueryOfferingsRequest.Builder queryOfferingsRequest = QueryOfferingsRequest.newBuilder();
        queryOfferingsRequest.setPage(page);
        queryOfferingsRequest.setOfferingsParams(offeringsParams);

        QueryOfferingsReply queryOfferingsReply = orderServiceBlockingStub.queryOfferings(queryOfferingsRequest.build());
        System.out.println("queryOfferingsReply: " + queryOfferingsReply);
        User user = new User();
        user.setId(queryOfferingsReply.getResult().getCode());
        user.setName(queryOfferingsReply.getResult().getMsg());
        user.setProduct(queryOfferingsReply.getOfferingList(0).getOfferingName());
        return user;
    }

    @RequestMapping("/queryFreeUnit")
    public User queryFreeUnit(@RequestBody GrpcRequest grpcRequest){

        QueryFreeUnitRequest.Builder request = QueryFreeUnitRequest.newBuilder();
        FreeUnitParams.Builder params = FreeUnitParams.newBuilder();
        params.setEntityId(grpcRequest.getEntityId());
        params.setEntityType(grpcRequest.getEntityType());
        params.setBeId(grpcRequest.getBeId());
        params.setCustId(grpcRequest.getCustId());

        Page page = Page.newBuilder().setTotal(1000).setIndex(1).setSize(10).setChannel("WEB").build();
        request.setFreeUnitParams(params).setPage(page);


        QueryFreeUnitReply queryFreeUnitReply = orderServiceBlockingStub.queryFreeUnit(request.build());
        System.out.println("queryFreeUnitReply: " + queryFreeUnitReply);
        User user = new User();
        user.setId(queryFreeUnitReply.getResult().getCode());
        user.setName(queryFreeUnitReply.getResult().getMsg());
        user.setProduct(String.valueOf(queryFreeUnitReply.getAccmMarginListCount()));
        return user;
    }

    @PostMapping("/queryCardInfo")
    public List<CardInfo> queryCardInfo(@RequestBody GrpcRequest grpcRequest){

        QueryCardInfoRequest.Builder request = QueryCardInfoRequest.newBuilder();
        CardInfoParamsRpc.Builder params = CardInfoParamsRpc.newBuilder();
        params.setEntityType(grpcRequest.getEntityType());
        params.setEntityId(grpcRequest.getEntityId());
        Page page = Page.newBuilder().setTotal(1000).setIndex(1).setSize(10).setChannel("WEB").build();
        request.setPage(page);

        request.setCardInfoParams(params);

        QueryCardInfoReply queryCardInfoReply = cmServiceBlockingStub.queryCardInfo(request.build());

        System.out.println("queryCardInfoReply: " + queryCardInfoReply);

        List<CardInfo> list = new ArrayList<CardInfo>();

        for(int i = 0; i < queryCardInfoReply.getCardInfoListCount(); i++){
            CardInfoRpc cardInfoRpc = queryCardInfoReply.getCardInfoList(i);
            CardInfo cardInfo = new CardInfo();
            doCopy(cardInfoRpc, cardInfo);
            /*cardInfo.setBeId(cardInfoRpc.getBeId());
            cardInfo.setCustId(cardInfoRpc.getCustId());
            cardInfo.setMsisdn(cardInfoRpc.getMsisdn());
            cardInfo.setImsi(cardInfoRpc.getImsi());
            cardInfo.setIccid(cardInfoRpc.getIccid());
            cardInfo.setStatus(cardInfoRpc.getStatus());
            cardInfo.setNetType(cardInfoRpc.getNetType());
            cardInfo.setSubsId(cardInfoRpc.getSubsId());*/
            list.add(cardInfo);
        }
        return list;
    }

    @RequestMapping("/queryOnStatus")
    public void queryOnStatus(@RequestBody GrpcRequest grpcRequest){
        QueryOnOffRequest.Builder request = QueryOnOffRequest.newBuilder();
        QueryOnOffParamsRpc.Builder params = QueryOnOffParamsRpc.newBuilder();
        params.setEntityType(grpcRequest.getEntityType());
        params.setEntityId(grpcRequest.getEntityId());
        params.setIsmi(grpcRequest.getImsi());
        request.setQueryOnOffParams(params);

        QueryOnOffReply onOffReply = cmServiceBlockingStub.querySubStatus(request.build());
        System.out.println("onOffReply---->>" + grpcRequest.getEntityId() + "-->" + onOffReply.getQuerystatus());
    }

    @RequestMapping("/signPayment")
    public void signPayment(@RequestBody PaymentRequest orderInfo){
        OrderPayRequest.Builder request = OrderPayRequest.newBuilder();
        OrderPayParamsRpc.Builder params = OrderPayParamsRpc.newBuilder();
        params.setBeId(orderInfo.getBeId());
        params.setCustId(orderInfo.getCustId());
        params.setAccountId(orderInfo.getAccountId());
        params.setOrderNo(orderInfo.getOrderNo());
        params.setChargeOrder(orderInfo.getChargeOrder());
        params.setChargeMoney(orderInfo.getChargeMoney());
        params.setPayment(orderInfo.getPaymentMoney());
        params.setPaymentType("WEIXIN-JSAPI");
        params.setGift(orderInfo.getGift());
        params.setProductId("1");
        params.setProductName("充值缴费");
        params.setProductDesc("充值缴费");
        params.setIdType("07");
        params.setIdValue(orderInfo.getAccountCode());
        params.setDefaultBank("ABC");

        request.setOrderPayParams(params);

        OrderPayReply payment = billServiceBlockingStub.payment(request.build());
    }


    @RequestMapping("/payment")
    public String payment(@RequestBody GrpcRequest grpcRequest){

        CreateOrderRequest.Builder bidRequest = CreateOrderRequest.newBuilder();
        CreateOrderParamsRpc.Builder bidParams = CreateOrderParamsRpc.newBuilder();
        bidParams.setAccountName(grpcRequest.getAccountName());
        bidParams.setEntityId(grpcRequest.getEntityId());
        bidParams.setEntityType(grpcRequest.getEntityType());
        bidParams.setCustId(grpcRequest.getCustId());
        bidParams.setBeId(grpcRequest.getBeId());
        bidParams.setChargeMoney(grpcRequest.getChargeMoney());
        bidRequest.setBidParams(bidParams);

        CreateOrderReply response = billServiceBlockingStub.createOrder(bidRequest.build());
        CreateOrderResultRpc orderInfo = response.getOrderInfo();
        System.out.println(orderInfo);

        OrderPayRequest.Builder request = OrderPayRequest.newBuilder();
        OrderPayParamsRpc.Builder params = OrderPayParamsRpc.newBuilder();
        params.setBeId(orderInfo.getBeId());
        params.setCustId(orderInfo.getCustId());
        params.setAccountId(orderInfo.getAccountId());
        params.setOrderNo(orderInfo.getOrderNo());
        params.setChargeOrder(orderInfo.getChargeOrder());
        params.setChargeMoney(orderInfo.getChargeMoney());
        params.setPayment(orderInfo.getPaymentMoney());
        params.setPaymentType("WEIXIN-JSAPI");
        params.setGift(orderInfo.getGift());
        params.setProductId("1");
        params.setProductName("充值缴费");
        params.setProductDesc("充值缴费");
        params.setIdType("07");
        params.setIdValue(orderInfo.getAccountCode());
        params.setDefaultBank("ABC");

        request.setOrderPayParams(params);

        OrderPayReply payment = billServiceBlockingStub.payment(request.build());
        System.out.println(payment.getOrderPay());
        return "22018050411000001465000";
    }

    @RequestMapping("/queryBalance")
    public User queryBalance(String accountId){
        QueryBalanceRequest.Builder request = QueryBalanceRequest.newBuilder();
        BalanceParamsRpc.Builder builder = BalanceParamsRpc.newBuilder();
        builder.setEntityType("A");
        builder.setEntityId(accountId);
        builder.setBeId("100");
        builder.setCustId("1411000000338070");
        request.setBalanceParams(builder);

        QueryBalanceReply queryBalanceReply = billServiceBlockingStub.queryBalance(request.build());

        System.out.println("Response: " + queryBalanceReply);

        User user = new User();
        user.setId(queryBalanceReply.getResult().getCode());
        user.setName(queryBalanceReply.getResult().getMsg());
        user.setProduct(queryBalanceReply.getBalance().getAmount());
        return user;
    }

   /* @RequestMapping("/enterpriseIamge")
    public void enterpriseIamge(){


        EnterpriseImageRequest.Builder enterpriseImageRequest = EnterpriseImageRequest.newBuilder();
        EnterpriseImageParams.Builder enterpriseImageParams = EnterpriseImageParams.newBuilder();
        enterpriseImageParams.setCustCode("100070096100100000");
        enterpriseImageRequest.setEnterpriseImageParams(enterpriseImageParams);
        EnterpriseImageReply enterpriseImageReply = riskControlServiceBlockingStub.enterpriseImage(enterpriseImageRequest.build());
        System.out.println("enterpriseImageReply:" + enterpriseImageReply);
    }*/

    @RequestMapping("/createOrder")
    public OrderInfo createOrder(@RequestBody GrpcRequest grpcRequest){

        CreateOrderRequest.Builder bidRequest = CreateOrderRequest.newBuilder();
        CreateOrderParamsRpc.Builder bidParams = CreateOrderParamsRpc.newBuilder();
        bidParams.setAccountName(grpcRequest.getAccountName());
        bidParams.setEntityId(grpcRequest.getEntityId());
        bidParams.setEntityType(grpcRequest.getEntityType());
        bidParams.setCustId(grpcRequest.getCustId());
        bidParams.setBeId(grpcRequest.getBeId());
        bidParams.setChargeMoney(grpcRequest.getChargeMoney());
        bidRequest.setBidParams(bidParams);

        CreateOrderReply response = billServiceBlockingStub.createOrder(bidRequest.build());
        CreateOrderResultRpc orderInfo = response.getOrderInfo();

        OrderInfo order = new OrderInfo();
        BeanUtils.copyProperties(orderInfo, order);
        return order;
    }

    @RequestMapping("/SIMCardPortrait")
    public SIMCardPortrait riskControlService(){
        SIMCardPortraitRequest.Builder request = SIMCardPortraitRequest.newBuilder();
        Page.Builder page = Page.newBuilder();
        page.setChannel("API");
        page.setIndex(1);
        page.setSize(10);
        SIMCardPortraitParamsRpc.Builder params = SIMCardPortraitParamsRpc.newBuilder();
        params.setMsiSdn("10647036851");
        request.setPage(page).setSIMCardPortraitParams(params);
        SIMCardPortraitReply simCardPortraitReply = riskControlServiceBlockingStub.sIMCardPortrait(request.build());
        SIMCardPortraitRpc simCardPortraitRpc = simCardPortraitReply.getSIMCardPortrait(0);
        SIMCardPortrait simCardPortrait = new SIMCardPortrait();

        log.info("simCardPortraitReply -->>{}", simCardPortraitReply);
        doCopy(simCardPortraitRpc, simCardPortrait, "apnId", "serviceType");
        List<StringEntry> list = new ArrayList<>();
        for(int i = 0; i < simCardPortraitRpc.getListMapCount(); i++){
            StringEntry se = new StringEntry();
            doCopy(simCardPortraitRpc.getListMap(i), se);
            list.add(se);
        }
        simCardPortrait.setListMap(list);

        return simCardPortrait;
    }


    private void doCopy(Object source, Object target, String ...listProperties) {
        BeanUtils.copyProperties(source, target);
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        List<String> list = Arrays.asList(listProperties);
        for(String property : list){
            try {
                Field sourceField = sourceClass.getDeclaredField(property + "_");
                sourceField.setAccessible(true);

                PropertyDescriptor pd = new PropertyDescriptor(property, targetClass);
                Method writeMethod = pd.getWriteMethod();
                writeMethod.setAccessible(true);
                writeMethod.invoke(target, sourceField.get(source));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
