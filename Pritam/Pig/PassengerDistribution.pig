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

grouped = GROUP data BY passenger_count;

result = FOREACH grouped GENERATE
    group AS passenger_count,
    COUNT(data) AS total_rides;

ordered = ORDER result BY total_rides DESC;

STORE ordered INTO 'PigPassengerDistribution'
USING PigStorage(',');