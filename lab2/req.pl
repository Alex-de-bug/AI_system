
% Сервер для игры в Dayz
server(otvaga).
server(агония).
server(podpivas).
server(stalker_project).
server(dayz_survival).
server(zombie_land).
server(dream_team).
server(dark_zone).
server(urban_chaos).

% Страна
country(ru).
country(ch).

% Режим обзора
view('3pp').
view('2pp').

% Стиль игры
type_gameplay(pvp).
type_gameplay(pve).
type_gameplay(lite).
type_gameplay(rp).

% Карта в игре
type_map(chernarus_plus).
type_map(enoch).
type_map(livonia).
type_map(nmchernobyl).

% Версия клиента игры
version('1.25.158593').
version('1.25.158590').
version('1.24.154325').

% IP адрес сервера
sever_ip('185.207.214.165:2402').
sever_ip('109.248.4.48:2308').
sever_ip('185.207.214.124:2502').
sever_ip('212.22.93.113:2302').


%--------------------------------------------------------


% Страна где расположен сервер
server_country(otvaga, ru).
server_country(агония, ch).
server_country(podpivas, ru).
server_country(stalker_project, ru).
server_country(dayz_survival, ru).
server_country(zombie_land, ch).
server_country(dream_team, ru).
server_country(dark_zone, ru).
server_country(urban_chaos, ch).

% Режим обзора на сервере
server_view(otvaga, '3pp').
server_view(агония, '3pp').
server_view(podpivas, '2pp').
server_view(stalker_project, '2pp').
server_view(stalker_project, '3pp').
server_view(dayz_survival, '3pp').
server_view(zombie_land, '2pp').
server_view(dream_team, '3pp').
server_view(dark_zone, '2pp').
server_view(urban_chaos, '3pp').

% Стиль игры на сервере
server_type_gameplay(otvaga, pvp).
server_type_gameplay(агония, pve).
server_type_gameplay(podpivas, lite).
server_type_gameplay(stalker_project, pvp).
server_type_gameplay(stalker_project, rp).
server_type_gameplay(dayz_survival, pve).
server_type_gameplay(zombie_land, rp).
server_type_gameplay(dream_team, pvp).
server_type_gameplay(dark_zone, lite).
server_type_gameplay(urban_chaos, pve).

% Карта на сервере
server_type_map(otvaga, chernarus_plus).
server_type_map(агония, enoch).
server_type_map(podpivas, livonia).
server_type_map(stalker_project, nmchernobyl).
server_type_map(dayz_survival, chernarus_plus).
server_type_map(zombie_land, enoch).
server_type_map(dream_team, livonia).
server_type_map(dark_zone, nmchernobyl).
server_type_map(urban_chaos, enoch).

% Версия клиента игры поддерживаемая сервером
server_version(otvaga, '1.25.158593').
server_version(агония, '1.25.158593').
server_version(podpivas, '1.25.158590').
server_version(stalker_project, '1.24.154325').
server_version(dayz_survival, '1.25.158593').
server_version(zombie_land, '1.24.154325').
server_version(dream_team, '1.25.158590').
server_version(dark_zone, '1.25.158593').
server_version(urban_chaos, '1.25.158593').

% IP конкретного сервера
server_ip(otvaga, '185.207.214.165:2402').
server_ip(агония, '109.248.4.48:2308').
server_ip(podpivas, '185.207.214.124:2502').
server_ip(stalker_project, '212.22.93.113:2302').
server_ip(dayz_survival, '185.207.214.100:2702').
server_ip(zombie_land, '109.248.5.50:2408').
server_ip(dream_team, '185.207.214.150:2902').
server_ip(dark_zone, '212.22.93.120:2502').
server_ip(urban_chaos, '109.248.4.60:2202').

% Максимальный онлайн игроков на сервере
server_max_online(otvaga, 90).
server_max_online(агония, 40).
server_max_online(podpivas, 80).
server_max_online(stalker_project, 60).
server_max_online(dayz_survival, 70).
server_max_online(zombie_land, 50).
server_max_online(dream_team, 100).
server_max_online(dark_zone, 60).
server_max_online(urban_chaos, 80).


%--------------------------------------------------------


% Сервер с актуальной версией клиента
server_with_actual_game_cli(Server) :-
    server_version(Server, '1.25.158593').

% Сервера с требуемым пингом
sever_with_ping(Ping, Server) :-
    Ping > 70, server_country(Server, Country), Country \= ru;
    Ping =< 70, server_country(Server, ru).

% Сервер для разноса черепов
server_for_destroy_skull(Server) :- 
    server_max_online(Server, Online),
    Online > 50,
    server_type_gameplay(Server, pvp),
    server_view(Server, '3pp').

% Сервер для комфортной игры
server_for_comfort_gameplay(Server) :- 
    server_max_online(Server, Online),
    Online =< 80,
    (server_type_gameplay(Server, lite); server_type_gameplay(Server, pve)).

% Сервер для наслаждения видами
server_for_nice_view(Server) :- 
    server_view(Server, '3pp'),
    (server_type_map(Server, chernarus_plus);
    server_type_map(Server, nmchernobyl)).


% Правило для юзеров
server_for_user(Server, View, Type, Map) :- 
    server_view(Server, View),
    server_type_gameplay(Server, Type),
    server_type_map(Server, Map).
