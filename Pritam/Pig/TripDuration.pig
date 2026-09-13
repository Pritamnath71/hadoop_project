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

duration = FOREACH data GENERATE
    (double)(
        (ToDate(dropoff_datetime,'yyyy-MM-dd HH:mm:ss')
        - ToDate(pickup_datetime,'yyyy-MM-dd HH:mm:ss')) / 60000
    ) AS duration_min;

grouped = GROUP duration ALL;

result = FOREACH grouped GENERATE
    AVG(duration.duration_min) AS avg_duration,
    MAX(duration.duration_min) AS max_duration,
    MIN(duration.duration_min) AS min_duration;

STORE result INTO 'PigTripDuration'
USING PigStorage(',');