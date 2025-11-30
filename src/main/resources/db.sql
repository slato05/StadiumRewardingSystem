--
-- PostgreSQL database dump
--

-- Dumped from database version 15.12
-- Dumped by pg_dump version 15.12

-- Started on 2025-11-30 15:06:51

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 24777)
-- Name: fan; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fan (
    fan_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(254) NOT NULL,
    phone_number character varying(20),
    occupation character varying(10) NOT NULL,
    CONSTRAINT fan_occupation_check CHECK (((occupation)::text = ANY (ARRAY[('STU'::character varying)::text, ('EDU'::character varying)::text, ('MLT'::character varying)::text, ('OTH'::character varying)::text])))
);


ALTER TABLE public.fan OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24781)
-- Name: fan_fan_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.fan ALTER COLUMN fan_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.fan_fan_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 220 (class 1259 OID 24850)
-- Name: preference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.preference (
    preference_id integer NOT NULL,
    fan_id integer NOT NULL,
    stand_id character varying(10) NOT NULL,
    reservation_time timestamp without time zone NOT NULL
);


ALTER TABLE public.preference OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24853)
-- Name: preference_preference_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.preference ALTER COLUMN preference_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.preference_preference_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 24831)
-- Name: reward; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reward (
    reward_id integer NOT NULL,
    fan_id integer NOT NULL,
    seat_id character varying(10) NOT NULL,
    time_rewarded timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.reward OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24835)
-- Name: reward_reward_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.reward ALTER COLUMN reward_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reward_reward_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 217 (class 1259 OID 24820)
-- Name: seat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seat (
    seat_id character varying(10) NOT NULL,
    stand_id character varying(10) NOT NULL,
    is_reserved boolean DEFAULT false NOT NULL
);


ALTER TABLE public.seat OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 24799)
-- Name: stand; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stand (
    stand_id character varying(10) NOT NULL,
    stand_name character varying(50) NOT NULL,
    available_seats integer NOT NULL,
    discount_price numeric(8,2) NOT NULL,
    CONSTRAINT stand_available_seats_check CHECK ((available_seats >= 0)),
    CONSTRAINT stand_discount_price_check CHECK ((discount_price >= (0)::numeric))
);


ALTER TABLE public.stand OWNER TO postgres;

--
-- TOC entry 3359 (class 0 OID 24777)
-- Dependencies: 214
-- Data for Name: fan; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fan (fan_id, first_name, last_name, email, phone_number, occupation) FROM stdin;
\.


--
-- TOC entry 3365 (class 0 OID 24850)
-- Dependencies: 220
-- Data for Name: preference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.preference (preference_id, fan_id, stand_id, reservation_time) FROM stdin;
\.


--
-- TOC entry 3363 (class 0 OID 24831)
-- Dependencies: 218
-- Data for Name: reward; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reward (reward_id, fan_id, seat_id, time_rewarded) FROM stdin;
\.


--
-- TOC entry 3362 (class 0 OID 24820)
-- Dependencies: 217
-- Data for Name: seat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seat (seat_id, stand_id, is_reserved) FROM stdin;
E001	EAST	f
E002	EAST	f
E003	EAST	f
W501	WEST	f
W502	WEST	f
W503	WEST	f
S101	SOUTH	f
S102	SOUTH	f
S103	SOUTH	f
S104	SOUTH	f
S105	SOUTH	f
N601	NORTH	f
N602	NORTH	f
N603	NORTH	f
N604	NORTH	f
N605	NORTH	f
\.


--
-- TOC entry 3361 (class 0 OID 24799)
-- Dependencies: 216
-- Data for Name: stand; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stand (stand_id, stand_name, available_seats, discount_price) FROM stdin;
EAST	East Stand	3	10.00
WEST	West Stand	3	5.00
SOUTH	South Stand	5	2.00
NORTH	North Stand	5	2.00
\.


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 215
-- Name: fan_fan_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fan_fan_id_seq', 105, true);


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 221
-- Name: preference_preference_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.preference_preference_id_seq', 105, true);


--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 219
-- Name: reward_reward_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reward_reward_id_seq', 129, true);


--
-- TOC entry 3197 (class 2606 OID 24783)
-- Name: fan fan_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fan
    ADD CONSTRAINT fan_email_key UNIQUE (email);


--
-- TOC entry 3199 (class 2606 OID 24785)
-- Name: fan fan_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fan
    ADD CONSTRAINT fan_pkey PRIMARY KEY (fan_id);


--
-- TOC entry 3209 (class 2606 OID 24855)
-- Name: preference preference_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.preference
    ADD CONSTRAINT preference_pkey PRIMARY KEY (preference_id);


--
-- TOC entry 3205 (class 2606 OID 24837)
-- Name: reward reward_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT reward_pkey PRIMARY KEY (reward_id);


--
-- TOC entry 3203 (class 2606 OID 24825)
-- Name: seat seat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_pkey PRIMARY KEY (seat_id);


--
-- TOC entry 3201 (class 2606 OID 24805)
-- Name: stand stand_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stand
    ADD CONSTRAINT stand_pkey PRIMARY KEY (stand_id);


--
-- TOC entry 3211 (class 2606 OID 24857)
-- Name: preference uq_pref_fan; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.preference
    ADD CONSTRAINT uq_pref_fan UNIQUE (fan_id);


--
-- TOC entry 3207 (class 2606 OID 24839)
-- Name: reward uq_reward_seat; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT uq_reward_seat UNIQUE (seat_id);


--
-- TOC entry 3215 (class 2606 OID 24858)
-- Name: preference fk_pref_fan; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.preference
    ADD CONSTRAINT fk_pref_fan FOREIGN KEY (fan_id) REFERENCES public.fan(fan_id) ON DELETE CASCADE;


--
-- TOC entry 3216 (class 2606 OID 24863)
-- Name: preference fk_pref_stand; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.preference
    ADD CONSTRAINT fk_pref_stand FOREIGN KEY (stand_id) REFERENCES public.stand(stand_id) ON DELETE CASCADE;


--
-- TOC entry 3213 (class 2606 OID 24840)
-- Name: reward fk_reward_fan; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT fk_reward_fan FOREIGN KEY (fan_id) REFERENCES public.fan(fan_id) ON DELETE CASCADE;


--
-- TOC entry 3214 (class 2606 OID 24845)
-- Name: reward fk_reward_seat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT fk_reward_seat FOREIGN KEY (seat_id) REFERENCES public.seat(seat_id) ON DELETE CASCADE;


--
-- TOC entry 3212 (class 2606 OID 24826)
-- Name: seat fk_seat_stand; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT fk_seat_stand FOREIGN KEY (stand_id) REFERENCES public.stand(stand_id) ON DELETE CASCADE;


-- Completed on 2025-11-30 15:06:51

--
-- PostgreSQL database dump complete
--

