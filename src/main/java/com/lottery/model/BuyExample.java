package com.lottery.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BuyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUnitidIsNull() {
            addCriterion("unitid is null");
            return (Criteria) this;
        }

        public Criteria andUnitidIsNotNull() {
            addCriterion("unitid is not null");
            return (Criteria) this;
        }

        public Criteria andUnitidEqualTo(Integer value) {
            addCriterion("unitid =", value, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidNotEqualTo(Integer value) {
            addCriterion("unitid <>", value, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidGreaterThan(Integer value) {
            addCriterion("unitid >", value, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidGreaterThanOrEqualTo(Integer value) {
            addCriterion("unitid >=", value, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidLessThan(Integer value) {
            addCriterion("unitid <", value, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidLessThanOrEqualTo(Integer value) {
            addCriterion("unitid <=", value, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidIn(List<Integer> values) {
            addCriterion("unitid in", values, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidNotIn(List<Integer> values) {
            addCriterion("unitid not in", values, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidBetween(Integer value1, Integer value2) {
            addCriterion("unitid between", value1, value2, "unitid");
            return (Criteria) this;
        }

        public Criteria andUnitidNotBetween(Integer value1, Integer value2) {
            addCriterion("unitid not between", value1, value2, "unitid");
            return (Criteria) this;
        }

        public Criteria andBuydateIsNull() {
            addCriterion("buydate is null");
            return (Criteria) this;
        }

        public Criteria andBuydateIsNotNull() {
            addCriterion("buydate is not null");
            return (Criteria) this;
        }

        public Criteria andBuydateEqualTo(Date value) {
            addCriterion("buydate =", value, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateNotEqualTo(Date value) {
            addCriterion("buydate <>", value, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateGreaterThan(Date value) {
            addCriterion("buydate >", value, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateGreaterThanOrEqualTo(Date value) {
            addCriterion("buydate >=", value, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateLessThan(Date value) {
            addCriterion("buydate <", value, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateLessThanOrEqualTo(Date value) {
            addCriterion("buydate <=", value, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateIn(List<Date> values) {
            addCriterion("buydate in", values, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateNotIn(List<Date> values) {
            addCriterion("buydate not in", values, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateBetween(Date value1, Date value2) {
            addCriterion("buydate between", value1, value2, "buydate");
            return (Criteria) this;
        }

        public Criteria andBuydateNotBetween(Date value1, Date value2) {
            addCriterion("buydate not between", value1, value2, "buydate");
            return (Criteria) this;
        }

        public Criteria andExpiredateIsNull() {
            addCriterion("expiredate is null");
            return (Criteria) this;
        }

        public Criteria andExpiredateIsNotNull() {
            addCriterion("expiredate is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredateEqualTo(Date value) {
            addCriterion("expiredate =", value, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateNotEqualTo(Date value) {
            addCriterion("expiredate <>", value, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateGreaterThan(Date value) {
            addCriterion("expiredate >", value, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateGreaterThanOrEqualTo(Date value) {
            addCriterion("expiredate >=", value, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateLessThan(Date value) {
            addCriterion("expiredate <", value, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateLessThanOrEqualTo(Date value) {
            addCriterion("expiredate <=", value, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateIn(List<Date> values) {
            addCriterion("expiredate in", values, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateNotIn(List<Date> values) {
            addCriterion("expiredate not in", values, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateBetween(Date value1, Date value2) {
            addCriterion("expiredate between", value1, value2, "expiredate");
            return (Criteria) this;
        }

        public Criteria andExpiredateNotBetween(Date value1, Date value2) {
            addCriterion("expiredate not between", value1, value2, "expiredate");
            return (Criteria) this;
        }

        public Criteria andLotteryidIsNull() {
            addCriterion("lotteryid is null");
            return (Criteria) this;
        }

        public Criteria andLotteryidIsNotNull() {
            addCriterion("lotteryid is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryidEqualTo(Integer value) {
            addCriterion("lotteryid =", value, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidNotEqualTo(Integer value) {
            addCriterion("lotteryid <>", value, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidGreaterThan(Integer value) {
            addCriterion("lotteryid >", value, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lotteryid >=", value, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidLessThan(Integer value) {
            addCriterion("lotteryid <", value, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidLessThanOrEqualTo(Integer value) {
            addCriterion("lotteryid <=", value, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidIn(List<Integer> values) {
            addCriterion("lotteryid in", values, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidNotIn(List<Integer> values) {
            addCriterion("lotteryid not in", values, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidBetween(Integer value1, Integer value2) {
            addCriterion("lotteryid between", value1, value2, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andLotteryidNotBetween(Integer value1, Integer value2) {
            addCriterion("lotteryid not between", value1, value2, "lotteryid");
            return (Criteria) this;
        }

        public Criteria andProductidIsNull() {
            addCriterion("productid is null");
            return (Criteria) this;
        }

        public Criteria andProductidIsNotNull() {
            addCriterion("productid is not null");
            return (Criteria) this;
        }

        public Criteria andProductidEqualTo(Integer value) {
            addCriterion("productid =", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidNotEqualTo(Integer value) {
            addCriterion("productid <>", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidGreaterThan(Integer value) {
            addCriterion("productid >", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidGreaterThanOrEqualTo(Integer value) {
            addCriterion("productid >=", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidLessThan(Integer value) {
            addCriterion("productid <", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidLessThanOrEqualTo(Integer value) {
            addCriterion("productid <=", value, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidIn(List<Integer> values) {
            addCriterion("productid in", values, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidNotIn(List<Integer> values) {
            addCriterion("productid not in", values, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidBetween(Integer value1, Integer value2) {
            addCriterion("productid between", value1, value2, "productid");
            return (Criteria) this;
        }

        public Criteria andProductidNotBetween(Integer value1, Integer value2) {
            addCriterion("productid not between", value1, value2, "productid");
            return (Criteria) this;
        }

        public Criteria andOrdernumIsNull() {
            addCriterion("ordernum is null");
            return (Criteria) this;
        }

        public Criteria andOrdernumIsNotNull() {
            addCriterion("ordernum is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernumEqualTo(String value) {
            addCriterion("ordernum =", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotEqualTo(String value) {
            addCriterion("ordernum <>", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumGreaterThan(String value) {
            addCriterion("ordernum >", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumGreaterThanOrEqualTo(String value) {
            addCriterion("ordernum >=", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumLessThan(String value) {
            addCriterion("ordernum <", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumLessThanOrEqualTo(String value) {
            addCriterion("ordernum <=", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumLike(String value) {
            addCriterion("ordernum like", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotLike(String value) {
            addCriterion("ordernum not like", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumIn(List<String> values) {
            addCriterion("ordernum in", values, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotIn(List<String> values) {
            addCriterion("ordernum not in", values, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumBetween(String value1, String value2) {
            addCriterion("ordernum between", value1, value2, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotBetween(String value1, String value2) {
            addCriterion("ordernum not between", value1, value2, "ordernum");
            return (Criteria) this;
        }

        public Criteria andIspayIsNull() {
            addCriterion("ispay is null");
            return (Criteria) this;
        }

        public Criteria andIspayIsNotNull() {
            addCriterion("ispay is not null");
            return (Criteria) this;
        }

        public Criteria andIspayEqualTo(Integer value) {
            addCriterion("ispay =", value, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayNotEqualTo(Integer value) {
            addCriterion("ispay <>", value, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayGreaterThan(Integer value) {
            addCriterion("ispay >", value, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayGreaterThanOrEqualTo(Integer value) {
            addCriterion("ispay >=", value, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayLessThan(Integer value) {
            addCriterion("ispay <", value, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayLessThanOrEqualTo(Integer value) {
            addCriterion("ispay <=", value, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayIn(List<Integer> values) {
            addCriterion("ispay in", values, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayNotIn(List<Integer> values) {
            addCriterion("ispay not in", values, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayBetween(Integer value1, Integer value2) {
            addCriterion("ispay between", value1, value2, "ispay");
            return (Criteria) this;
        }

        public Criteria andIspayNotBetween(Integer value1, Integer value2) {
            addCriterion("ispay not between", value1, value2, "ispay");
            return (Criteria) this;
        }

        public Criteria andShareidIsNull() {
            addCriterion("shareid is null");
            return (Criteria) this;
        }

        public Criteria andShareidIsNotNull() {
            addCriterion("shareid is not null");
            return (Criteria) this;
        }

        public Criteria andShareidEqualTo(Integer value) {
            addCriterion("shareid =", value, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidNotEqualTo(Integer value) {
            addCriterion("shareid <>", value, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidGreaterThan(Integer value) {
            addCriterion("shareid >", value, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidGreaterThanOrEqualTo(Integer value) {
            addCriterion("shareid >=", value, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidLessThan(Integer value) {
            addCriterion("shareid <", value, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidLessThanOrEqualTo(Integer value) {
            addCriterion("shareid <=", value, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidIn(List<Integer> values) {
            addCriterion("shareid in", values, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidNotIn(List<Integer> values) {
            addCriterion("shareid not in", values, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidBetween(Integer value1, Integer value2) {
            addCriterion("shareid between", value1, value2, "shareid");
            return (Criteria) this;
        }

        public Criteria andShareidNotBetween(Integer value1, Integer value2) {
            addCriterion("shareid not between", value1, value2, "shareid");
            return (Criteria) this;
        }
        
        public Criteria andPrepayidIsNull() {
            addCriterion("prepayid is null");
            return (Criteria) this;
        }

        public Criteria andPrepayidIsNotNull() {
            addCriterion("prepayid is not null");
            return (Criteria) this;
        }

        public Criteria andPrepayidEqualTo(String value) {
            addCriterion("prepayid =", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidNotEqualTo(String value) {
            addCriterion("prepayid <>", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidGreaterThan(String value) {
            addCriterion("prepayid >", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidGreaterThanOrEqualTo(String value) {
            addCriterion("prepayid >=", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidLessThan(String value) {
            addCriterion("prepayid <", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidLessThanOrEqualTo(String value) {
            addCriterion("prepayid <=", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidLike(String value) {
            addCriterion("prepayid like", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidNotLike(String value) {
            addCriterion("prepayid not like", value, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidIn(List<String> values) {
            addCriterion("prepayid in", values, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidNotIn(List<String> values) {
            addCriterion("prepayid not in", values, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidBetween(String value1, String value2) {
            addCriterion("prepayid between", value1, value2, "prepayid");
            return (Criteria) this;
        }

        public Criteria andPrepayidNotBetween(String value1, String value2) {
            addCriterion("prepayid not between", value1, value2, "prepayid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}