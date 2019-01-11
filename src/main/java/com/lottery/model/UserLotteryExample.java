package com.lottery.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserLotteryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserLotteryExample() {
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

        public Criteria andLotterydateIsNull() {
            addCriterion("lotterydate is null");
            return (Criteria) this;
        }

        public Criteria andLotterydateIsNotNull() {
            addCriterion("lotterydate is not null");
            return (Criteria) this;
        }

        public Criteria andLotterydateEqualTo(Date value) {
            addCriterion("lotterydate =", value, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateNotEqualTo(Date value) {
            addCriterion("lotterydate <>", value, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateGreaterThan(Date value) {
            addCriterion("lotterydate >", value, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateGreaterThanOrEqualTo(Date value) {
            addCriterion("lotterydate >=", value, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateLessThan(Date value) {
            addCriterion("lotterydate <", value, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateLessThanOrEqualTo(Date value) {
            addCriterion("lotterydate <=", value, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateIn(List<Date> values) {
            addCriterion("lotterydate in", values, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateNotIn(List<Date> values) {
            addCriterion("lotterydate not in", values, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateBetween(Date value1, Date value2) {
            addCriterion("lotterydate between", value1, value2, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotterydateNotBetween(Date value1, Date value2) {
            addCriterion("lotterydate not between", value1, value2, "lotterydate");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidIsNull() {
            addCriterion("lotteryitemid is null");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidIsNotNull() {
            addCriterion("lotteryitemid is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidEqualTo(Integer value) {
            addCriterion("lotteryitemid =", value, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidNotEqualTo(Integer value) {
            addCriterion("lotteryitemid <>", value, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidGreaterThan(Integer value) {
            addCriterion("lotteryitemid >", value, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lotteryitemid >=", value, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidLessThan(Integer value) {
            addCriterion("lotteryitemid <", value, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidLessThanOrEqualTo(Integer value) {
            addCriterion("lotteryitemid <=", value, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidIn(List<Integer> values) {
            addCriterion("lotteryitemid in", values, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidNotIn(List<Integer> values) {
            addCriterion("lotteryitemid not in", values, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidBetween(Integer value1, Integer value2) {
            addCriterion("lotteryitemid between", value1, value2, "lotteryitemid");
            return (Criteria) this;
        }

        public Criteria andLotteryitemidNotBetween(Integer value1, Integer value2) {
            addCriterion("lotteryitemid not between", value1, value2, "lotteryitemid");
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

        public Criteria andExchangedateIsNull() {
            addCriterion("exchangedate is null");
            return (Criteria) this;
        }

        public Criteria andExchangedateIsNotNull() {
            addCriterion("exchangedate is not null");
            return (Criteria) this;
        }

        public Criteria andExchangedateEqualTo(Date value) {
            addCriterion("exchangedate =", value, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateNotEqualTo(Date value) {
            addCriterion("exchangedate <>", value, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateGreaterThan(Date value) {
            addCriterion("exchangedate >", value, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateGreaterThanOrEqualTo(Date value) {
            addCriterion("exchangedate >=", value, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateLessThan(Date value) {
            addCriterion("exchangedate <", value, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateLessThanOrEqualTo(Date value) {
            addCriterion("exchangedate <=", value, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateIn(List<Date> values) {
            addCriterion("exchangedate in", values, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateNotIn(List<Date> values) {
            addCriterion("exchangedate not in", values, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateBetween(Date value1, Date value2) {
            addCriterion("exchangedate between", value1, value2, "exchangedate");
            return (Criteria) this;
        }

        public Criteria andExchangedateNotBetween(Date value1, Date value2) {
            addCriterion("exchangedate not between", value1, value2, "exchangedate");
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

        public Criteria andPrizenumIsNull() {
            addCriterion("prizenum is null");
            return (Criteria) this;
        }

        public Criteria andPrizenumIsNotNull() {
            addCriterion("prizenum is not null");
            return (Criteria) this;
        }

        public Criteria andPrizenumEqualTo(String value) {
            addCriterion("prizenum =", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumNotEqualTo(String value) {
            addCriterion("prizenum <>", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumGreaterThan(String value) {
            addCriterion("prizenum >", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumGreaterThanOrEqualTo(String value) {
            addCriterion("prizenum >=", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumLessThan(String value) {
            addCriterion("prizenum <", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumLessThanOrEqualTo(String value) {
            addCriterion("prizenum <=", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumLike(String value) {
            addCriterion("prizenum like", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumNotLike(String value) {
            addCriterion("prizenum not like", value, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumIn(List<String> values) {
            addCriterion("prizenum in", values, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumNotIn(List<String> values) {
            addCriterion("prizenum not in", values, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumBetween(String value1, String value2) {
            addCriterion("prizenum between", value1, value2, "prizenum");
            return (Criteria) this;
        }

        public Criteria andPrizenumNotBetween(String value1, String value2) {
            addCriterion("prizenum not between", value1, value2, "prizenum");
            return (Criteria) this;
        }

        public Criteria andSharenumIsNull() {
            addCriterion("sharenum is null");
            return (Criteria) this;
        }

        public Criteria andSharenumIsNotNull() {
            addCriterion("sharenum is not null");
            return (Criteria) this;
        }

        public Criteria andSharenumEqualTo(String value) {
            addCriterion("sharenum =", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumNotEqualTo(String value) {
            addCriterion("sharenum <>", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumGreaterThan(String value) {
            addCriterion("sharenum >", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumGreaterThanOrEqualTo(String value) {
            addCriterion("sharenum >=", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumLessThan(String value) {
            addCriterion("sharenum <", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumLessThanOrEqualTo(String value) {
            addCriterion("sharenum <=", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumLike(String value) {
            addCriterion("sharenum like", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumNotLike(String value) {
            addCriterion("sharenum not like", value, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumIn(List<String> values) {
            addCriterion("sharenum in", values, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumNotIn(List<String> values) {
            addCriterion("sharenum not in", values, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumBetween(String value1, String value2) {
            addCriterion("sharenum between", value1, value2, "sharenum");
            return (Criteria) this;
        }

        public Criteria andSharenumNotBetween(String value1, String value2) {
            addCriterion("sharenum not between", value1, value2, "sharenum");
            return (Criteria) this;
        }
        
        public Criteria andOtheruseridIsNull() {
            addCriterion("otheruserid is null");
            return (Criteria) this;
        }

        public Criteria andOtheruseridIsNotNull() {
            addCriterion("otheruserid is not null");
            return (Criteria) this;
        }

        public Criteria andOtheruseridEqualTo(Integer value) {
            addCriterion("otheruserid =", value, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridNotEqualTo(Integer value) {
            addCriterion("otheruserid <>", value, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridGreaterThan(Integer value) {
            addCriterion("otheruserid >", value, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("otheruserid >=", value, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridLessThan(Integer value) {
            addCriterion("otheruserid <", value, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridLessThanOrEqualTo(Integer value) {
            addCriterion("otheruserid <=", value, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridIn(List<Integer> values) {
            addCriterion("otheruserid in", values, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridNotIn(List<Integer> values) {
            addCriterion("otheruserid not in", values, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridBetween(Integer value1, Integer value2) {
            addCriterion("otheruserid between", value1, value2, "otheruserid");
            return (Criteria) this;
        }

        public Criteria andOtheruseridNotBetween(Integer value1, Integer value2) {
            addCriterion("otheruserid not between", value1, value2, "otheruserid");
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