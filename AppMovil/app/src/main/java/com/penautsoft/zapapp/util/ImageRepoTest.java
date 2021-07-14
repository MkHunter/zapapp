package com.penautsoft.zapapp.util;

import java.util.Random;

public class ImageRepoTest {

    private static String[] repo = {
            "https://i.ibb.co/CKhWBXH/FB-IMG-1593138823774.jpg",
            "https://i.ibb.co/xH8c7my/FB-IMG-1590690948286.jpg",
            "https://i.ibb.co/KV48p6V/FB-IMG-1601959754688.jpg",
            "https://i.ibb.co/BBV99BK/White-Concrete.png",
            "https://i.ibb.co/thswkDN/dDfxzKag.png",
            "https://i.ibb.co/4tpQmfP/chayanne.jpg",
            "https://i.ibb.co/2YDGzMN/www-keralahousedesigns-com.jpg",
            "https://i.ibb.co/1XkPKbK/whiskas-pariente.jpg",
            "https://i.ibb.co/vPtLmQ8/soriana-logo.jpg",
            "https://i.ibb.co/9Wf5nGp/Bounty.jpg",
            "https://i.ibb.co/RH7jGY3/unnamed.jpg",
            "https://i.ibb.co/G2FmvN9/fondo-gato.jpg",
            "https://i.ibb.co/w6mSKLk/FB-IMG-1591246753939.jpg",
            "https://i.ibb.co/mSHQsrK/FB-IMG-1594044170929.jpg"
            /*"https://lh3.googleusercontent.com/YaSwpSowiowDgvw47sgc6bhITgdcZHsdJ9esaHsWh55-Z3gC9BKF7BlyMDZ8pNDx3Sowodj00WLUepo3MHJWKCKSn7BxNxJVP7ejpD5Vtx0u2kQOZwL0w7XO5uy7kBZU-ZqvUGf2gZe022NVkQql1ar6iJ0JHRNXFhg8nUUKTqdNh2NFyr0Ba4O4z_yKbcA9eULomdMqeOF4OsLbI0vN4B7Rp405e2iMUKoOw_oG-cSzqTs_hdvFzAsucf-1tEgFZJEs1Wkca7HDL8xQRkpaEhP44lBN9oUOH7FCMfU9evklUyERwAP4FUWFrMGKEX__UY7pCmSgBMahnfOIANn8Z_RyNIMssHPzI-_Q2BOr-FazVi5mpU55srcWSk6ThDaht_7-vGm2L8S1ntfIKnTyTolrRycCViKgki4NAktmdt_e6fQ2Uu5fcefahEpMq5C-yClqJkOF7tZ6SQymr0MpgbHVu2S67-lsCzzrAbpULVEqj80JJNnVbVGVfmoSvcoJFBp1t0FuLZ-Qduvjab2F4ZTQIRJY4_IFjbKHh1McU20NMzQTpWbka1k_5U4ZwrfQbmVu5HCYKgzMU5sC7tIyF-WSLYV9snBFVsyR0RxHZXARi3S0mVhlYtKaf1XnHG1rXg1qDxgGYEqFnxIePrF5Y_SxGKEpWEsAf-LGDSxzjaTf_9noIB9aehVnaUTUjjY=w1366-h768-no?authuser=0",
            "https://lh3.googleusercontent.com/8Tg-zmuckr4sfoQSlqsS5E6-P-eZ5-wfxro_mY1mub3Pm44XNyci4u-7lzz-b9VYkTafp3bMVDCsPu_VZkA_0EXq9qF1wO3xcApeSkKsKx_9LybRSOBKJ8TWLKjLgRpbJpdmnTfdOfhHri4O8U_NG9Vuwqps7gZdEHwlndtuoou6Xrk9DZ7TEXbbMKySriNII4QcCvyOFwwL6JUWVOd9Va_wdnz6fwIjlzOv5tLpjof42oN95TFbFXlZWqetPGALSjltp5xRt0PqCClsWpOmylnURBVISJv-M2o41Ze7YPEaOnmENMh0pfFb2XdPIooG0PeseQEMupWYNu3Bmct0GZAYJgP5lGgBIJnD4CTyP6qlGlTJWVcr1b-TCtv_l9YTbw7pmP6Oc6u0WnlpYarOJhLcY4rjXjtxfzgJFbHmMrOHMA8bSViQoTGeYjreFw7T77p8LgjR9um-dJWB--SVgkWR5BUM-Kw98JgZgrGBT3IzIG9oF5VpoTMg0nLkBasK52RqmOzHZU2ZiuIsLFoeToHr2hW2bVSTqsrQJO1wXFkao0JGzrU9_-2c3tOTcFI0aW7Ng_40ACOZjn2ondmQAtsj7tBytjMTjdvXqw7tC5lOYYWxnXct6wH82HBIDeCz7t6MSSKq0Ejx6C1kHRHYQ5Jl_yVEIJWqSwZlSkOWLVTcibLr6-1grYZ89BahqA8=w937-h577-no?authuser=0",
            "https://lh3.googleusercontent.com/HuOIwLKKXuLFWTkmNqJEE2a4aOlsNr-6TfOTUt5kamoCAf2sf7Y7DbEIuHiTAObg9lsN2FQ5myuAy6Hd60Zj0wIyNDxvUQwNtGk5VZIjDw7umz5PFHSVd5up8ZaGfv_dbNpnEKf3aUj9mzaGuH_fBOTgTUpEaAc69ppkt4eI3Qc-8m8OQKAOeDbbHrKQNVPKawgo5K5lbrCPC1z_mxm3Jg3rfZFIpeBNu7rsbAfiDVBGQ922oHWwb2SJ061eACuXI5iJflggUYh2RHcfxmwZ7Jyhs3w1ulHBwICKb7OG0GnB6gFwYFX2A2z-UDdBnuK5WUozKd3ktAh-NEaR-3dwa_OOFUVrrVVW-fiYYwtHcUZPapt0ueqKRSR21xCYjkFxbJoW816mqkOXtL0f33p_KwDGUh4VEHWQdNkCq1kW9NM8rzf71GgvmVk6GR_p7Gk91EPcyDwGlOOJ4J3RS9v-PhWtkC5820FINSWRoqNmhQx3whiywptoQ_lTk3-Zks-2VzuqR8Um_5Z2xnuIghufP9D14ixHhYrOav7RYHZVvkh5QMwT5gJY0rX2W_9oWK7Aiu25FsfRAQfvLtun_9ACLkeZRLJOltebNdaemT24oKoWJ77XWWrbXXZqfZAihmtpQDwg5ot0pVHDRvuuKsXTw_EPVI1Motf17o6CCre-ixiV0cUqsBjGENfGseZTOVE=s450-no?authuser=0",
            "https://lh3.googleusercontent.com/RPN_DF4_5zgb8kjzgDwuIsCS_yZqpKCYLbmbHr-73yBbtPX8fcUh7A3PzKXK-BE1A65yUaY8gMdovtDvA7DcVNGduxq6a_BK5mWnLND6ZGr3LN53h2S49a5QTvlGBuc8kF3mrNBlpSTJ9U76IhMEv5QeltUkwvdi2x_5QKH50MUUsUx2wsu6xMPwt01Uj_glKi09EpninGMYsCoxyF-hYyIwVubGZuh1b8t6tb_DTbskQXamOhulveMUdju8N7xQqrBgaLg4mHhWtvq4xFR74Q33a5a_2gbJWuURpDWwOsBUGD8EBqmR7HdJ9UtVJhw-1XKXkh5TLQyE2D7YVJGd2vwAIxSi-g9uvBIpQG1e0i8HbCqLejAO07ez8HvwhoajIHByC9asUSLBaZBBvQrFYjf9fh6g6FGlR51Q5o8TvflDA4LS5kmHULfqwQomvLef1RHyRGtg1KzVYXJ0OjZy458UgTQlCzQAI56reI9-PDWdPH1sotXH96PAuy2iXyGuSKZU8EgOlvKDECpk1dg-IgY9WiCfoNI-aVpocsTc04V7B3RXEILoGIaPFKI-yUrd-_mBOxMWYxybOGesu822z6gm5SycAfsm7CwnlbcstxnAWLoqfDv4Y566KR4NtvRKPCxAnKXC1pQOp0vYnVI1UFd2Wny1Pfzmdvgyn9NkS0cwb7mTdPWYxxRfHmw-toI=w1366-h768-no?authuser=0",
            "https://lh3.googleusercontent.com/H6y875HKsd6eW_gDnVEVBJsgtqGP1RmGAIBXrKnckYxxDBLKFY5BxpNirrfqwS-JO1x1sobYhseftUSNS0zj8wuexrVfOylrUEUBjTBCsrUnIU5ddtbdrju5YkFqE2JH-tJNEcuelq8JEhZzPogWtkbGMTZ4EesgcRRI80IaJYhRTrcI5pouNfmj_ed_lq7QXxI7GQP4zlMaioIK_P0Xo657wNVdcqYHlbHwYVSKN63NWkTqekSqnmBDjZrpXokipysf3vy-rEQ3UXI0aOQMUxA2w9ViSFcyqOw4LLbmq2SMkPCuVxCYCcCS4vweOviFArsEgBhpYakyKEm_cQf11sCPse-OupGu79GLt-lOvX7hTuSJ_ga4CLZAl0NB4VGga2DotmxXTJWnICNv1o01vZ_VxGrTwFaN73rk5PCmMTUfRMeo3poPnPR0VjGXwJD-nTxbPXU8SE8r7QBx4U3DCEGaFUPSnRd7aT2s7qrIHadWeu8bLECW_qMHW4BQEi3G9FXbCIpo0byMUmyo_ba9POFj5ythZ_noQDNTN21p9o3EhqT5H9vY1m4V8K9dfX2c3lLr8d2wniDgtMLA9bQX8QBH9Pkra6IDSJzMOVzGd8YwLb8LTvD-AMDYccMKbSjSWuaV_xdSHxYnsZ7DzTOxMJmdB7GqpheAF0E8aGXI4gpny4AiwbChLxBGeB4KuM0=w706-h363-no?authuser=0",
            "https://lh3.googleusercontent.com/RseSWJovhEUhMxZihLiWbQDLxkpLDdBfK_k0P34WVjH42QGVFd7a9w9i2CQeGjLw76OV9WQ14ejAVsnLIlEvnG01QWa592pGLv5CwzA440KvweMEkr0rU_X3uMYZgrNcCSFhDL7Pfa8zDZqIV0R7yt4rSkNrKvQVfaM05Vd5CANRVNX-X6uGqXUfXicZ4lVzIJNnoUL-60b9BnWXnJzYVZ6IhksF54NDhKTgfLkrC4m0vS45_WtUGt8AZS6OlZNzi57PoiogV5cmProbhn3SJj9nqCKYQ4GqUN_1Yv0uNJUZBuGe7CJ7CYYi2kN2DVpceRH4CKgzF68DHqeaHn2GQuKru9AwIcnhc7kp2KK0lDpIF0G86z2_yDnB81DBwu8AdIyDsUx0x3XOM6vD4fhCEkFJG29uxX-MvJKIsmauSxOgL6m4XRwFYSfthTqUvCzXtH_Fa7B4Gtw-dHYuPRmlukKo9Mk7PhK6SOsA7qHBBjWSMuFuTFt6q6n6EoIagBEOGpDWx5ap3gdOvarrXkNp1zapdHrKekxT_LaK5ExVIwdSUue5GswYu14llhVXySqdI4UwPq6aPI-DeuJOMtifyU0CD38x__4p5czjRget2eBdXxsVylQETmLNjVkrJ00TDN0H76Tv36WuVuXBFko67FL_VZIywgRa3Aklwh_t_tCctknNIkLSTDAgy2Whs9A=s903-no?authuser=0"*/
    };


    public static String retImageUrl(){
        Random rn = new Random();
        try{
            return repo[rn.nextInt(repo.length)];
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return "";
        }
    }

    public static String[] getAll(){
        return repo;
    }
}
