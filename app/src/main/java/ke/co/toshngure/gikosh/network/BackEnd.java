package ke.co.toshngure.gikosh.network;

/**
 * Created by Anthony Ngure on 04/06/2017.
 * Email : anthonyngure25@gmail.com.
 * http://www.toshngure.co.ke
 */

public class BackEnd {

    public static final String BASE_URL = "https://gikosh.toshngure.co.ke/api/v1";

    public static final class EndPoints {
        public static final String CATEGORIES = "/categories";
        public static final String DEVICE_TOKEN = "/deviceToken";
        public static final String PHONE_SIGN_IN = "/auth/phoneSignIn";
        public static final String PHONE_SIGN_UP = "/auth/phoneSignUp";
        public static final String FACEBOOK_SIGN_IN = "/auth/facebookSignIn";
    }

    public static final class Params {
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String NAME = "name";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String GENDER = "gender";
        public static final String AGE_RANGE_MIN = "ageRangeMin";
        public static final String PICTURE_URL = "pictureUrl";
        public static final String FACEBOOK_ID = "facebookId";
    }

    public static final class Response {
        public static final String DATA = "data";
        public static final String META = "meta";
        public static final String MESSAGE = "message";
        public static final String ERROR_CODE = "errorCode";
    }

    public static final class ErrorCodes {
        public static final String EMAIL_EXISTS = "EMAIL_EXISTS";
        public static final String SAME_PHONE = "SAME_PHONE";
        public static final String FEED_NOT_FOUND = "FEED_NOT_FOUND";
        public static final String PAYOUT_ALREADY_MADE = "PAYOUT_ALREADY_MADE";
        public static final String PHONE_NOT_FOUND = "PHONE_NOT_FOUND";
        public static final String WRONG_VERIFICATION_CODE = "WRONG_VERIFICATION_CODE";
        public static final String TOKEN_MISSING = "TOKEN_MISSING";
        public static final String TOKEN_INVALID = "TOKEN_INVALID";
        public static final String WRONG_PASSWORD = "WRONG_PASSWORD";
        public static final String MISSING_PARAMS = "MISSING_PARAMS";
        public static final String SERVER_ERROR = "SERVER_ERROR";
        public static final String PHONE_EXISTS = "PHONE_EXISTS";
        public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
        public static final String PHONE_NOT_VERIFIED = "PHONE_NOT_VERIFIED";
        public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
        public static final String UNAUTHORIZED = "UNAUTHORIZED";
        public static final String WITHDRAWAL_ERROR = "WITHDRAWAL_ERROR";
        public static final String BONUS_ERROR = "BONUS_ERROR";
        public static final String REVIEW_ALREADY_MADE = "REVIEW_ALREADY_MADE";
        public static final String ACCOUNT_NOT_SETUP = "ACCOUNT_NOT_SETUP";
        public static final String OBSOLETE = "OBSOLETE";
    }
}
