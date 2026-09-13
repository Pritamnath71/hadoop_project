data = LOAD 'taxisvis1M.csv'
USING PigStorage(',')
AS (
    VendorID:int,
    pickup_datetime:chararray,
    dropoff_datetime:chararray,
    passenger_count:int,
    trip_distance:double,
    pickup_longitude:double,
    pickup_latitude:double,
    RateCodeID:int,
    store_and_fwd_flag:chararray,
    dropoff_longitude:double,
    dropoff_latitude:double,
    payment_type:int,
    fare_amount:double,
    extra:double,
    mta_tax:double,
    tip_amount:double,
    tolls_amount:double,
    improvement_surcharge:double,
    total_amount:double
);

data = FILTER data BY VendorID IS NOT NULL;
data = FILTER data BY fare_amount > 0;

tip_calc = FOREACH data GENERATE
    (tip_amount / fare_amount) * 100 AS tip_percent;

grouped = GROUP tip_calc ALL;

result = FOREACH grouped GENERATE
    AVG(tip_calc.tip_percent) AS avg_tip_percent,
    MAX(tip_calc.tip_percent) AS max_tip_percent,
    MIN(tip_calc.tip_percent) AS min_tip_percent;

STORE result INTO 'PigTipPercentage'
USING PigStorage(',');