FilterConfig = {
    --src_host= "",
    --src_port = 56789,
    --dst_host = "",
    --min_sn = 123,
    --max_sn = 65500,
    --csi = 0x763c
    --dst_port = 9002,
    count = 1
}


StreamDirection = {  
    STREAM_UNKNOWN = 0,
    STREAM_SENDING = 1,
    STREAM_RECVING = 2
}


CryptoSuiteType = {
    CST_NULL_CIPHER_HMAC_SHA1_80 = 0,
    CST_AES_CM_128_HMAC_SHA1_80 = 1,
    CST_AES_CM_SW_128_HMAC_SHA1_80 = 2,
    CST_AES_CM_SW_128_HMAC_SHA1_OSW_80 =3,
}

SecurityService = {
    SEC_SERVICE_NONE = 0,
    SEC_SERVICE_CONFIDENTIALITY = 1,
    SEC_SERVICE_AUTHENTICATION = 2,
    SEC_SERVICE_CONFI_AUTH = 3
}

SRTPFecOder = {
    ORDER_SRTP_FEC = 0,
    ORDER_FEC_SRTP = 1
}

EKTCipherType = 
{
    EKT_CIPHER_AESKW_128 = 0
}

SecurityConfiguration = {
    cryptoSuiteType = CryptoSuiteType.CST_AES_CM_SW_128_HMAC_SHA1_OSW_80,
    masterKeySalt="OWc4MTRNeTBpbmFpa3EwNWFiY2RlZmdoaWprbG1u",
    masterKeySaltLength=30, --16 bytes master key and 14 bytes salt
    rtpSecurityService = SecurityService.SEC_SERVICE_CONFI_AUTH,
    rtcpSecurityService = SecurityService.SEC_SERVICE_CONFI_AUTH,
    fecOder = SRTPFecOder.ORDER_SRTP_FEC,
    ekt = 
    {
        cipherType = EKTCipherType.EKT_CIPHER_AESKW_128,
        key = "bzJKM1Y3NU4zWDAyN1dQMQ==",
        keyLength = 16,
        spi = 10791
    },
    switchingRTPPacket = 0, 
    stripSRTPTagsAfterUnprotect = 0, 
    allowReplayedPacket = 0,
}

--print("master key salt:" .. SecurityConfiguration.masterKeySalt)
--print("ekt key: " .. SecurityConfiguration.ekt.key);

