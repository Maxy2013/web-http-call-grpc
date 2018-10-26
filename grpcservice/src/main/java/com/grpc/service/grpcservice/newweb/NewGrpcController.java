package com.grpc.service.grpcservice.newweb;

import com.grpc.service.grpcservice.base.GrpcBase;
import com.grpc.service.grpcservice.base.Params;
import com.grpc.service.grpcservice.base.User;
import onelink.api.oneservice.account.service.AccountInfoParams;
import onelink.api.oneservice.account.service.QueryAccountInfoReply;
import onelink.api.oneservice.account.service.QueryAccountInfoRequest;
import onelink.api.oneservice.bill.service.*;
import onelink.api.oneservice.cm.service.*;
import onelink.api.oneservice.common.Page;
import onelink.api.oneservice.order.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        AccountInfoParams.Builder params = AccountInfoParams.newBuilder();
        params.setEntityType("S");
        params.setEntityId(phone);
        request.setAccountInfoParams(params);

        QueryAccountInfoReply queryAccountInfoReply = accountServiceBlockingStub.queryAccountInfo(request.build());
        for (int i = 0; i < queryAccountInfoReply.getAccountListCount(); i++){
            log.info("queryAccountInfoReply--->{}", queryAccountInfoReply.getAccountList(i).getAccountName());
        }
    }

    @RequestMapping("/queryOfferings")
    public User queryOfferings(){

        OfferingsParams.Builder offeringsParams = OfferingsParams.newBuilder();
        offeringsParams.setEntityId("1440101405000");
        offeringsParams.setEntityType("M");
        offeringsParams.setBeId("731");
        offeringsParams.setCustId("11000001730000");

        Page page = Page.newBuilder().setTotal("1000").setIndex("1").setSize("10").setChannel("WEB").build();

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
    public User queryFreeUnit(Params param){

        QueryFreeUnitRequest.Builder request = QueryFreeUnitRequest.newBuilder();
        FreeUnitParams.Builder params = FreeUnitParams.newBuilder();
        params.setEntityId(param.getMsisdn());
        params.setEntityType("M");
        params.setBeId(param.getBeId());
        params.setCustId(param.getCustId());

        Page page = Page.newBuilder().setTotal("1000").setIndex("1").setSize("10").setChannel("WEB").build();
        request.setFreeUnitParams(params).setPage(page);


        QueryFreeUnitReply queryFreeUnitReply = orderServiceBlockingStub.queryFreeUnit(request.build());
        System.out.println("queryFreeUnitReply: " + queryFreeUnitReply);
        User user = new User();
        user.setId(queryFreeUnitReply.getResult().getCode());
        user.setName(queryFreeUnitReply.getResult().getMsg());
        user.setProduct(String.valueOf(queryFreeUnitReply.getAccmMarginListCount()));
        return user;
    }

    @RequestMapping("/queryCardInfo")
    public User queryCardInfo(){

        QueryCardInfoRequest.Builder request = QueryCardInfoRequest.newBuilder();
        CardInfoParams.Builder params = CardInfoParams.newBuilder();
        params.setEntityType("S");
        params.setEntityId("18390929026");

        request.setCardInfoParams(params);

        QueryCardInfoReply queryCardInfoReply = cmServiceBlockingStub.queryCardInfo(request.build());

        System.out.println("queryCardInfoReply: " + queryCardInfoReply);
        User user = new User();
        user.setId(queryCardInfoReply.getResult().getCode());
        user.setName(queryCardInfoReply.getResult().getMsg());
        user.setProduct(String.valueOf(queryCardInfoReply.getCardInfoListCount()));
        return user;
    }

    @RequestMapping("/queryOnStatus")
    public void queryOnStatus(){
        QueryOnOffRequest.Builder request = QueryOnOffRequest.newBuilder();
        QueryOnOffParams.Builder params = QueryOnOffParams.newBuilder();
        params.setEntityType("M");
        params.setEntityId("1440101405000");
        params.setIsmi("460043014005000");
        request.setQueryOnOffParams(params);

        QueryOnOffReply onOffReply = cmServiceBlockingStub.querySubStatus(request.build());
        System.out.println("onOffReply---->>" + onOffReply.getQuerystatus());
    }


    @RequestMapping("/payment")
    public void payment(){
        OrderRequest.Builder request = OrderRequest.newBuilder();
        OrderPayParams.Builder params = OrderPayParams.newBuilder();
        params.setBeId("731");
        params.setCustId("11000001730000");
        params.setAccountId("11000001467000");
        params.setOrderNo("00792018102517250242288574688990");
        params.setChargeOrder("00792018102517250242288574688990");
        params.setChargeMoney("1000");
        params.setPayment("1000");
        params.setPaymentType("ALIPAY-WEB");
        params.setGift("0");
        params.setProductId("1");
        params.setProductName("充值缴费");
        params.setProductDesc("充值缴费");
        params.setIdType("07");
        params.setIdValue("22018050311000001455000");

        request.setOrderPayParams(params);

        OrderResponse payment = billServiceBlockingStub.payment(request.build());
        System.out.println(payment.getOrderPay());
    }

    @RequestMapping("/queryBalance")
    public User queryBalance(String accountId){
        QueryBalanceRequest.Builder request = QueryBalanceRequest.newBuilder();
        BalanceParams.Builder builder = BalanceParams.newBuilder();
        builder.setEntityType("A");
        builder.setEntityId(accountId);
        builder.setBeId("731");
        builder.setCustId("11000001730000");
        request.setBalanceParams(builder);

        QueryBalanceReply queryBalanceReply = billServiceBlockingStub.queryBalance(request.build());

        System.out.println("Response: " + queryBalanceReply);

        User user = new User();
        user.setId(queryBalanceReply.getResult().getCode());
        user.setName(queryBalanceReply.getResult().getMsg());
        user.setProduct(queryBalanceReply.getBalance().getAmount());
        return user;
    }

}
