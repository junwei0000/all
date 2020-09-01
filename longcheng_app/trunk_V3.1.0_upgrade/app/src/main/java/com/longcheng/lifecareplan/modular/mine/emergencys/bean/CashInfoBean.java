package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;

public class CashInfoBean extends ResponseBean {
    public CashBean data;

    public static class CashBean {
        public Card card;
        public String url;
        public String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public Card getCard() {
            return card;
        }

        public void setCard(Card card) {
            this.card = card;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class Card {
            public String knp_team_bind_card_id;
            public String user_id;
            public String bank_name;
            public String bank_abbre;
            public String bank_no;
            public String cardholder_name;
            public String bank_full_name;
            public String status;
            public String create_time;
            public String update_time;


            public String getKnp_team_bind_card_id() {
                return knp_team_bind_card_id;
            }

            public void setKnp_team_bind_card_id(String knp_team_bind_card_id) {
                this.knp_team_bind_card_id = knp_team_bind_card_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getBank_abbre() {
                return bank_abbre;
            }

            public void setBank_abbre(String bank_abbre) {
                this.bank_abbre = bank_abbre;
            }

            public String getBank_no() {
                return bank_no;
            }

            public void setBank_no(String bank_no) {
                this.bank_no = bank_no;
            }

            public String getCardholder_name() {
                return cardholder_name;
            }

            public void setCardholder_name(String cardholder_name) {
                this.cardholder_name = cardholder_name;
            }

            public String getBank_full_name() {
                return bank_full_name;
            }

            public void setBank_full_name(String bank_full_name) {
                this.bank_full_name = bank_full_name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            @Override
            public String toString() {
                return "Card{" +
                        "knp_team_bind_card_id='" + knp_team_bind_card_id + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", bank_name='" + bank_name + '\'' +
                        ", bank_abbre='" + bank_abbre + '\'' +
                        ", bank_no='" + bank_no + '\'' +
                        ", cardholder_name='" + cardholder_name + '\'' +
                        ", bank_full_name='" + bank_full_name + '\'' +
                        ", status='" + status + '\'' +
                        ", create_time='" + create_time + '\'' +
                        ", update_time='" + update_time + '\'' +
                        '}';
            }
        }

    }

    public CashBean getData() {
        return data;
    }

    public void setData(CashBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EmergencyListBean{" +
                "data=" + data +
                '}';
    }


}
