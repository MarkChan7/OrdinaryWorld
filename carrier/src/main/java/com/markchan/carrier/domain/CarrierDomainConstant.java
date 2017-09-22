package com.markchan.carrier.domain;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2017/9/22
 */
public interface CarrierDomainConstant {

    interface DATA_SOURCE {

        /*
        -> 0x00             0000       00
             data source    stand by   need download or not
         */
        int ALL = 0x00000000;

        int ASSET = 0x10000001;
        int ONLINE = 0x01000010;
        int SDCARD = 0x01000001; // online -> [downloaded] -> sdcard

        int ONLINE_AND_SDCARD = 0x01000011;
        int ASSET_AND_SDCARD = 0x11000001;
    }
}
