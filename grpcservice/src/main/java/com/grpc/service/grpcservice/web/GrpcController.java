package com.grpc.service.grpcservice.web;

import com.grpc.service.grpcservice.base.GrpcBase;
import com.grpc.service.grpcservice.base.User;
import onelink.api.oneservice.account.service.AccountInfoParams;
import onelink.api.oneservice.account.service.QueryAccountInfoReply;
import onelink.api.oneservice.account.service.QueryAccountInfoRequest;
import onelink.api.oneservice.bill.service.*;
import onelink.api.oneservice.cm.service.*;
import onelink.api.oneservice.common.Page;
import onelink.api.oneservice.order.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 * 2018/10/18 14:36
 */
@RestController
public class GrpcController extends GrpcBase{

    @RequestMapping("/queryOfferings")
    public User queryOfferings(){

        OfferingsParams.Builder offeringsParams = OfferingsParams.newBuilder();
        offeringsParams.setEntityId("1440100000156");
        offeringsParams.setEntityId("1440100100123");
        offeringsParams.setEntityId("1440100300002");
        offeringsParams.setEntityId("1440100100124");
        offeringsParams.setEntityId("1440100200010");
        offeringsParams.setEntityType("M");

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

    @RequestMapping("/bid")
    public String bid(){

        Page page = Page.newBuilder().setTotal("1000").setIndex("1").setSize("10").setChannel("WEB").build();

        // createOrder
        BidParams.Builder bid = BidParams.newBuilder();
        bid.setEntityType("A");
        bid.setEntityId("11000001467000");
        bid.setBeId("731");
        bid.setCustId("11000001730000");
        bid.setAccountName("长沙窄带网测试单位");
        bid.setChargeMoney("10");

        BidRequest build = BidRequest.newBuilder().setPage(page).setBidParams(bid).build();

        BidResponse orderInfo = billServiceBlockingStub.bid(build);

        System.out.println("Response: " + orderInfo);
        return orderInfo.getResult().getMsg();
    }

    @RequestMapping("/queryAccount")
    public User account(String phone){

        Page page = Page.newBuilder().setTotal("1000").setIndex("1").setSize("10").setChannel("WEB").build();

        // createOrder
        AccountInfoParams.Builder accountInfo = AccountInfoParams.newBuilder();
        accountInfo.setEntityType("S");
        accountInfo.setEntityId("13196891113");
        accountInfo.setEntityId(phone);

        QueryAccountInfoRequest.Builder queryAccountRequest = QueryAccountInfoRequest.newBuilder();
        queryAccountRequest.setPage(page);
        queryAccountRequest.setAccountInfoParams(accountInfo);

        QueryAccountInfoReply queryAccountInfoReply = accountServiceBlockingStub.queryAccountInfo(queryAccountRequest.build());

        System.out.println("queryAccountInfoReply: " + queryAccountInfoReply.toString());
        User user = new User();
        user.setId(queryAccountInfoReply.getResult().getCode());
        user.setName(queryAccountInfoReply.getResult().getMsg());
        user.setProduct(queryAccountInfoReply.getAccountList(0).getAccountName());
        System.out.println("打印内容-->>>>: " + user.getProduct());
        return user;
    }

    @RequestMapping("/queryFreeUnit")
    public User queryFreeUnit(String msisdn){
        QueryFreeUnitRequest.Builder request = QueryFreeUnitRequest.newBuilder();
        FreeUnitParams.Builder builder = FreeUnitParams.newBuilder();
        builder.setEntityType("M");
        builder.setEntityId(msisdn);
        request.setFreeUnitParams(builder);

        QueryFreeUnitReply queryFreeUnitReply = orderServiceBlockingStub.queryFreeUnit(request.build());
        System.out.println("Response: " + queryFreeUnitReply);
        User user = new User();
        user.setId(queryFreeUnitReply.getResult().getCode());
        user.setName(queryFreeUnitReply.getResult().getMsg());
        user.setProduct(queryFreeUnitReply.getAccmMarginList(0).getOfferingName());
        return user;
    }

    @RequestMapping("/queryCardInfo")
    public User queryCardInfo(String phone){
        QueryCardInfoRequest.Builder request = QueryCardInfoRequest.newBuilder();
        CardInfoParams.Builder builder = CardInfoParams.newBuilder();
        builder.setEntityType("S");
        builder.setEntityId(phone);
        request.setCardInfoParams(builder);

        QueryCardInfoReply queryCardInfoReply = cmServiceBlockingStub.queryCardInfo(request.build());
        System.out.println("Response: " + queryCardInfoReply);
        User user = new User();
        user.setId(queryCardInfoReply.getResult().getCode());
        user.setName(queryCardInfoReply.getResult().getMsg());
        user.setProduct(queryCardInfoReply.getCardInfoList(0).getCustId());
        return user;
    }

    @RequestMapping("/queryCardStatus")
    public User queryCardStatus(String msisdn){
        QueryOnOffRequest.Builder request = QueryOnOffRequest.newBuilder();
        QueryOnOffParams.Builder builder = QueryOnOffParams.newBuilder();
        builder.setEntityType("M");
        builder.setEntityId(msisdn);
        request.setQueryOnOffParams(builder);

        QueryOnOffReply queryOnOffReply = cmServiceBlockingStub.querySubStatus(request.build());

        System.out.println("Response: " + queryOnOffReply);

        User user = new User();
        user.setId(queryOnOffReply.getResult().getCode());
        user.setName(queryOnOffReply.getResult().getMsg());
        user.setProduct(queryOnOffReply.getQuerystatus().getStatus());
        return user;
    }

    @RequestMapping("/queryBalance")
    public User queryBalance(String accountId){
        QueryBalanceRequest.Builder request = QueryBalanceRequest.newBuilder();
        BalanceParams.Builder builder = BalanceParams.newBuilder();
        builder.setEntityType("A");
        builder.setEntityId(accountId);
        builder.setBeId("100");
        builder.setCustId("12312");
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
