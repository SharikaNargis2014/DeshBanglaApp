package com.deshbangla.shrimpandfish.network;

import com.deshbangla.shrimpandfish.response.BannerResponse;
import com.deshbangla.shrimpandfish.response.CartItemDeleteResponse;
import com.deshbangla.shrimpandfish.response.CartItemPostResponse;
import com.deshbangla.shrimpandfish.response.CartItemResponse;
import com.deshbangla.shrimpandfish.response.CartItemUpdateResponse;
import com.deshbangla.shrimpandfish.response.CategoryResponse;
import com.deshbangla.shrimpandfish.response.IndividualOrderDetailsResponse;
import com.deshbangla.shrimpandfish.response.OrderDetailsResponse;
import com.deshbangla.shrimpandfish.response.ProductDetailsResponse;
import com.deshbangla.shrimpandfish.response.ProductResponse;
import com.deshbangla.shrimpandfish.response.SearchResponse;
import com.deshbangla.shrimpandfish.response.ShippingResponse;
import com.deshbangla.shrimpandfish.response.SignUpResponse;
import com.deshbangla.shrimpandfish.response.UserResponse;
import com.deshbangla.shrimpandfish.response.WishListPostResponse;
import com.deshbangla.shrimpandfish.response.WishListResponse;
import com.deshbangla.shrimpandfish.response.WishlistDeleteResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("feature_product")
    Call<ProductResponse> getFeaturedProducts(@Query("page") int page);

    @GET("category/product/{slug}")
    Call<ProductResponse> getProductsByCategory(
            @Path("slug")String slug,
            @Query("page")int page);

    @GET("product-detail/{id}")
    Call<ProductDetailsResponse> getProductDetails(@Path("id") int id);

    @GET("product")
    Call<ProductResponse> getAllProducts(@Query("page") int page);

    @GET("category")
    Call<CategoryResponse> getCategories();

    @GET("banner")
    Call<BannerResponse> getBanners();

    @GET("search")
    Call<SearchResponse> getSearchedItem(
            @Query("search") String searchedItem,
            @Query("page")int page);

    @POST("login")
    Call<UserResponse> loginUser(
            @Query("email") String email,
            @Query("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<SignUpResponse> signUpUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String passConfirm);

    @GET("wishlist/{id}")
    Call<WishListResponse> getWishList(@Path("id") int id);

    @FormUrlEncoded
    @POST("your_shipping/add/{id}")
    Call<ShippingResponse> postShippingInfo(
            @Path("id") int id,
            @Field("customer_name") String customerName,
            @Field("email") String email,
            @Field("phone_num") String phoneNum,
            @Field("address") String address,
            @Field("message") String message,
            @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("wishlist_store/{id}")
    Call<WishListPostResponse> postWish(
            @Path("id") int id,
            @Field("user_id") int userId,
            @Field("product_id") int product_id);

    @POST("wishlist-delete/{id}")
    Call<WishlistDeleteResponse> deleteWishlistItem(@Path("id") int id);

    @FormUrlEncoded
    @POST("cart/add/{id}")
    Call<CartItemPostResponse> postToCart(@Path("id") int id,
                                          @Field("user_id") int user_id,
                                          @Field("image") String image,
                                          @Field("name")String name,
                                          @Field("price")String price,
                                          @Field("qty")String qty);

    @FormUrlEncoded
    @POST("cart/update/{id}")
    Call<CartItemUpdateResponse> updateQty(@Path("id")int id,
                                           @Field("user_id") int user_id,
                                           @Field("qty") String qty);

    @POST("cart/remove/{id}")
    Call<CartItemDeleteResponse> deleteCartItem(@Path("id")int id);

    @GET("cart/{id}")
    Call<CartItemResponse> getCartItems(@Path("id")int id);

    @GET("pending-order/{id}")
    Call<OrderDetailsResponse> getPendingOrders(@Path("id") int id);

    @GET("order-history/{id}")
    Call<OrderDetailsResponse> getDeliveredOrders(@Path("id") int id);

    @GET("order-details/{id}")
    Call<IndividualOrderDetailsResponse> getOrderDetails(@Path("id") int id, @Query("order_id") int orderId);

}
