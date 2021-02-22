<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="./css/welcome-page.css">
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/sign.css">
    <link rel="stylesheet" href="./css/footer.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/v4-shims.css">
    <link rel="stylesheet" href="./css/hotel_profile.css">
    <link rel="stylesheet" href="./css/photo_upload.css">
    <link rel="stylesheet" href="./css/sign.css">
    <link rel="stylesheet" href="./css/selection.css" />
    <link rel="stylesheet" href="./css/selection-results.css">
    <link rel="stylesheet" href="./css/sidebar_test.css">
    
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
    
    
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    
    <fmt:message bundle="${loc}" key="local.signup" var="signup" />
    <fmt:message bundle="${loc}" key="local.signup.headline" var="signup_headline" />
    <fmt:message bundle="${loc}" key="local.signup.name" var="signup_name" />
    <fmt:message bundle="${loc}" key="local.signup.name.hint" var="signup_name_hint" />  
    <fmt:message bundle="${loc}" key="local.signup.surname" var="signup_surname" />
    <fmt:message bundle="${loc}" key="local.signup.surname.hint" var="signup_surname_hint" />  
    <fmt:message bundle="${loc}" key="local.signup.age" var="signup_age" />
    <fmt:message bundle="${loc}" key="local.signup.email" var="signup_email" />
    <fmt:message bundle="${loc}" key="local.signup.email.message" var="signup_email_message" />
    <fmt:message bundle="${loc}" key="local.signup.password" var="signup_password" /> 
    <fmt:message bundle="${loc}" key="local.signup.password.hint" var="signup_password_hint" />  
    <fmt:message bundle="${loc}" key="local.signup.traveller" var="signup_traveller" />
    <fmt:message bundle="${loc}" key="local.signup.hotelier" var="signup_hotelier" />
    <fmt:message bundle="${loc}" key="local.signup.submit" var="signup_submit" />
    <fmt:message bundle="${loc}" key="local.signin" var="signin" />
    <fmt:message bundle="${loc}" key="local.signin.headline" var="signin_headline" />
    <fmt:message bundle="${loc}" key="local.signin.email" var="signin_email" />
    <fmt:message bundle="${loc}" key="local.signin.password" var="signin_password" />
    <fmt:message bundle="${loc}" key="local.signin.password.hint" var="signin_password_hint" />
    <fmt:message bundle="${loc}" key="local.signin.submit" var="signin_submit" />
    <fmt:message bundle="${loc}" key="local.signout" var="signout" />
    <fmt:message bundle="${loc}" key="local.footer.about" var="footer_about" />
    <fmt:message bundle="${loc}" key="local.footer.about.content" var="footer_about_content" />
    <fmt:message bundle="${loc}" key="local.footer.address" var="footer_address" />
    <fmt:message bundle="${loc}" key="local.footer.address.content" var="footer_address_content" />
    <fmt:message bundle="${loc}" key="local.footer.contact" var="footer_contact" />
    <fmt:message bundle="${loc}" key="local.footer.message" var="footer_message" />
    <fmt:message bundle="${loc}" key="local.footer.submit" var="footer_submit" />
    <fmt:message bundle="${loc}" key="local.welcome.destination" var="welcome_destination" />
    <fmt:message bundle="${loc}" key="local.welcome.destination.placeholder" var="welcome_destination_placeholder" />
    <fmt:message bundle="${loc}" key="local.welcome.checkin" var="welcome_checkin" />
    <fmt:message bundle="${loc}" key="local.welcome.checkout" var="welcome_checkout" />
    <fmt:message bundle="${loc}" key="local.welcome.guests" var="welcome_guests" />
    <fmt:message bundle="${loc}" key="local.welcome.rooms" var="welcome_rooms" />
    <fmt:message bundle="${loc}" key="local.welcome.search" var="welcome_search" />
    
    
    <title>REDWOOD</title>
  </head>
  <body>
    <c:set var="user" value="${user}" />
    <c:set var="msg" value="${message}" />
    <c:set var="overlay" value="${overlay}" />
    <c:set var="sign_in_msg" value="${sign_in_msg}" />
    <c:set var="hotel" value="${hotel}"/>
    
    <nav>
      <input type="checkbox" id="check">
      <label for="check">
        <i class="fas fa-bars" id="btn" style="margin-bottom: -20px;"></i>
        <i class="fas fa-times" id="cancel" style="margin-bottom: -20px;"></i>
      </label>
      <div class="header__logo">
          REDWOOD
        </div>
      <ul>

       <!--overlay for sign up form -->
        
        <c:choose>
          <c:when test="${not empty overlay}">
            <div id="mySignUpOverlay" class="show-overlay">
          </c:when>
          <c:when test="${empty overlay}">
            <div id="mySignUpOverlay" class="sign_up_overlay">
          </c:when>
        </c:choose>
          <span class="closebtn close_sign_up" title="closeOverlay">&#215 </span>
          <div class="wrap-form">
            <h2>${signup_headline}</h2>
            
            <form class="sign" action = "Controller" method = "post">
              <ul id ="form-messages" class ="signup-messages">
                <li>${signup_email_message}</li>
              </ul>
              <c:choose>
              <c:when test="${not empty msg}">              
              	<input class="error_placeholder" type="email" placeholder="${msg}" name="email" id="signup_email" required>                             
              </c:when>
              <c:when test="${empty msg}">
                <input type="email" placeholder="${signup_email}" name="email" id="signup_email" required>
              </c:when>
              </c:choose>                        
              <input type="hidden" name="command" value="sign_up" >
              <input type="text" placeholder="${signup_name}" name="name" pattern="[a-zA-Zа-яёА-ЯЁ]{2,15}" title="${signup_name_hint}" required>
              <input type="text" placeholder="${signup_surname}" name="surname" pattern="[a-zA-Zа-яёА-ЯЁ]{2,15}" title="${signup_surname_hint}" required>
              <input type="password" placeholder="${signup_password}" name="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,12}$" title="${signup_password_hint}" minlength = 6 required>
              <input type="password" placeholder="${signup_password}" name="repassword" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,12}$" title="${signup_password_hint}" minlength = 6 required>
              <div class="inline">
                <input type="radio" id="role-traveller" name="role" value="2" required> <label for="role-traveller">${signup_traveller}</label>
              </div>
              <div class="inline">
                <input type="radio" id="role-hotelier" name="role" value="3" required> <label for="role-hotelier">${signup_hotelier}</label>
              </div>
              <input id="submitRegistration" type="submit" value="${signup_submit}">
            </form>
          </div>
        </div>

        <!-- overlay for sign in form -->
        
        <c:choose>
          <c:when test="${not empty sign_in_msg}">
            <div id="mySignInOverlay" class="show-overlay">
          </c:when>
          <c:when test="${empty sign_in_msg}">
            <div id="mySignInOverlay" class="sign_in_overlay">
          </c:when>
        </c:choose>
        
          <span class="closebtn close_sign_in" onclick="closeSignInForm()" title="closeOverlay"> &#215</span>
          <div class="wrap-form">
            <h2>${signin_headline}</h2>
            <form class="sign" action = "Controller" method = "post">
            <h5>
              <c:if test="${not empty sign_in_msg}">
                <c:out value="${sign_in_msg}"/>
              </c:if>
            </h5>
              <input type="hidden" name="command" value="sign_in">
              <input type="email" placeholder="${signin_email}" name="email" required>
              <input type="password" placeholder="${signin_password}" name="password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,12}$" title="${signin_password_hint}" minlength = 6 required>
              <input id="submitLogination" type="submit" value="${signin_submit}">
            </form>
          </div>
        </div>
        
        <c:choose>
          <c:when test="${empty user}">
            <li><a class="open_sign_up" id="open_sign_up" href="#">${signup} </a></li>
          </c:when>
          <c:when test="${not empty user}">
            <li class="header-link header-link__text username">
              <a class="open_account" href="Controller?command=show_profile" onclick="get_account()">
                <c:out value="${user.name} ${user.surname}"/>
              </a>
            </li> 
          </c:when>
        </c:choose>
        
        <c:choose>
          <c:when test="${empty user}">
            <li><a class="open_sign_in"href="#">${signin}</a></li>
          </c:when>
          <c:when test="${not empty user}">
            <form class="hidden_out_form" name="sign_out_form" action="Controller" method="post">
              <input type="hidden" name="command" value="sign_out">
            </form>
           <li><a class="sign_out" onclick="document.sign_out_form.submit();"href="#">${signout}</a></li>
          </c:when>
         </c:choose>
         <form class="hidden_locale_form" name = "ru_locale_form" action="Controller" method="post">
           <input type="hidden" name="command" value="localization" /> 
           <input type="hidden" name="local" value="ru" />
         </form>
        <li><a onclick="document.ru_locale_form.submit();" href="#">RUS</a></li>
        <form class="hidden_locale_form" name = "en_locale_form" action="Controller" method="post">
           <input type="hidden" name="command" value="localization" /> 
           <input type="hidden" name="local" value="en" />
         </form>
        <li><a onclick="document.en_locale_form.submit();" href="#">ENG</a></li>
      </ul>
    </nav>
    
    
    
    
      
  <!-- welcome page -->
  <!-- PERSONAL ACCOUNT HOTEL-->
  
  <c:choose>
  
  
    <c:when test="${empty hotelier && empty search_outcome}">
      
      <div class="main">
        <div class="wrapper">
          <div class="form">
          <form action="Controller" method="post">
            <div class="inputfield">
              <input type="hidden" name="command" value="search_hotels_by_input">
              <label>${welcome_destination}</label>
              <input type="text" class="input" name="searchbar_input" value="" placeholder="${welcome_destination_placeholder}">
            </div>
            <div class="inputfield ">
              <label>${welcome_checkin}</label>
              <input class="datefield" type="date" name="from" id="currentDate">
            </div>
            <div class="inputfield">
              <label>${welcome_checkout}</label>
              <input class="datefield" type="date" name="till" id="nextDate" required>
            </div>
            <div class="inputfield">
              <label>${welcome_guests}</label>
              <input type="number" value="1" class="number" min="1">
            </div>
            <div class="inputfield">
              <label>${welcome_rooms}</label>
              <input type="number" value="1" class="number" min="1">
            </div>
            <div class="inputfield">
              <input type="submit" class="btn" value="${welcome_search}">
            </div>
            </form>
          </div>
        </div>
      </div>

    </c:when>
    
    <c:when test="${empty hotelier && not empty search_outcome}">
    <%@include file="./jsp/search_outcome.jsp" %>
	</c:when>
	
  	<c:when test="${not empty hotelier}">
  	  <div class="hotel-profile-main">
      <div class="hotel-profile-container">
        <div class="leftbox">
          <nav>
            <a href="#" onclick="tabs(0)" class="tab">
              <i class="fa fa-user"></i>
            </a>
            <a href="#" onclick="tabs(1)" class="tab">
              <i class="fas fa-hotel"></i>
            </a>
            <a href="#" onclick="tabs(2)" class="tab">
              <i class="fas fa-images"></i>
            </a>
            <a href="#" onclick="tabs(3)" class="tab">
              <i class="fas fa-bed"></i>
            </a>
            <a href="#" onclick="tabs(4)" class="tab">
              <i class="fas fa-utensils"></i>
            </a>
            <a href="#" onclick="tabs(5)" class="tab">
              <i class="fas fa-parking"></i>
            </a>
            <a href="#" onclick="tabs(5)" class="tab">
              <i class="fas fa-chart-bar"></i>
            </a>

          </nav>
        </div>
        <div class="rightbox">
          <form class="hotelier-profile tab-show">
            <h1>Personal Info</h1>
            <h2>Name</h2>
            
            <input type="text" class="profile-input "name="name" value="${user.name}">
            <h2>Surname</h2>
            <input type="text" class="profile-input "name="" value="${user.surname}">
            <h2>Email</h2>
            <input type="email" class="profile-input "name="" value="${user.email}">
            <h2>Password</h2>
            <input type="password" class="profile-input "name="" value="">
            <button type="submit" class="profile-submit-btn">Update</button>
          </form>
          <form class="hotel-profile tab-show" action = "Controller" method = "post">
            <h1>Hotel Info</h1>
            <h2>Name</h2>
            <input type="hidden" name="command" value="register_hotel">
            <input type="text" class="profile-input "name="name" value="${hotel.name}" placeholder="Hotel Name">
            <h2>Address</h2>
            <input id="country-list" type="text" list="countries" name = "country" placeholder="Country" value="${hotel.address.country}"/>
            <datalist id="countries">
              <c:forEach var="countryName" items="${countries}">
                <option>${countryName}</option>
              </c:forEach>
            </datalist>
            <input type="text" name = "city" value = "${hotel.address.city}" list="cities" placeholder="City"/>
            <datalist id="cities" value="London">
              <c:forEach var="city" items="${cities}">
                <option>${city}</option>
              </c:forEach>
            </datalist>
            <input type="text" class="profile-input "name="street" value="${hotel.address.street}" placeholder="Street">
            <input type="text" class="profile-input "name="building" value="${hotel.address.building}" placeholder="Building">
            <h2>Star Rating</h2>
            <input type="number" class="profile-input "name="stars" value="${hotel.starsNumber}" min="1" max="5" required>
            <input type="checkbox" id="wi-fi_profile-checkbox" name="wifi" value="1">
            <label for="wi-fi_profile-chekbox">Wi-Fi</label>
            <br>
            <button type="submit" class="profile-submit-btn">Update</button>
          </form>
          <form class="hotelier-photo-profile tab-show" action="upload" method="post" enctype="multipart/form-data">
            <h1>Hotel Photos</h1>
            <h2>Main Photo</h2>

            <div class="hotel-photo-profile-container">
              <div class="hotel-photo-profile-wrapper">
                <div class="hotel-photo-profile-image">
                  <img src="" alt="">
                </div>
                <div class="hotel-photo-profile-content">
                  <div class="hotel-photo-profile-icon">
                    <i class="fas fa-cloud-upload-alt"></i>
                  </div>
                  <div class="hotel-photo-profile-text">
                    No file chosen yet!
                  </div>
                </div>
                <div id="hotel-photo-profile-cancel-btn">
                  <i class="fas fa-times"></i>
                </div>
                <div class="hotel-photo-profile-file-name">
                  File name here
                </div>
              </div>
              <button type="button" onclick="defaultBtnActive()" id="hotel-photo-profile-custom-btn">Choose a file</button>
              <input id="hotel-photo-profile-default-btn" name = "content" type="file"  hidden>
            </div>
            <hr/>
              <h2>Extra photos</h2>
              <input type="file"/>
              <br>
              <input type="file" />
              <br>
              <input type="file"/>  
              <br />
              <button type="submit" class="profile-submit-btn">Update</button>

          </form>
          <form class="rooms-profile tab-show" action="Controller" method="post">
            <h1>Rooms</h1>
            <label for="roomtype">Room Type</label>
            <label for="rooms_number">Number</label>
            <label for="ppn">Price, €</label>
            <input type="hidden" name="command" value="register_rooms"/>
            
            <c:choose>
              <c:when test="${not empty hotel.roomFund.rooms}">
            <c:forEach var="entry" items="${hotel.roomFund.rooms}">
            <input id="roomtype" type="text" name="first_room_type" list="room_types" value="${entry.key}" placeholder="" />
            <datalist id="room_types">
              <option value="1">Single</option>
              <option value="2">Double</option>
              <option value="3">Deluxe</option>
            </datalist>

            <input id="rooms_number" type="number" name="first_room_number" value="${hotel.roomFund.numberOfRooms[entry.key]}" min="1" max="10" name="" value="">
			
            <input id="ppn" type="number" name="first_room_price" value="${entry.value}" step="any" min="0">

            </c:forEach>
            </c:when>
            <c:when test="${empty hotel.roomFund.rooms}">
            <input id="roomtype" type="text" name="first_room_type" list="room_types" value="" placeholder="" />
            <datalist id="room_types">
              <option value="1">Single</option>
              <option value="2">Double</option>
              <option value="3">Deluxe</option>
            </datalist>

            <input id="rooms_number" type="number" name="first_room_number" value="" min="1" max="10" name="" value="">
			
            <input id="ppn" type="number" name="first_room_price" value="" step="any" min="0">
            
            
            <input type="text" list="room_types2" name="second_room_type" value="" placeholder="" />
              <datalist id="room_types2">
                <option value="1">Single</option>
                <option value="2">Double</option>
                <option value="3">Deluxe</option>
              </datalist>

              <input id="rooms_number2" type="number" name="second_room_number" min="1" max="10" name="" value="">
              <input  type="number" name="second_room_price" value="" step="any" min="0">

              <input type="text" list="room_types3" name="third_room_type" value="" placeholder="" />
                <datalist id="room_types3">
                  <option value="1">Single</option>
                  <option value="2">Double</option>
                  <option value="3">Deluxe</option>
                </datalist>

                <input id="rooms_number3" type="number" name="third_room_number" min="1" max="10" name="" value="">
                <input  type="number" name="third_room_price" value="" step="any" min="0">
            </c:when>
            </c:choose>
            <button type="submit" class="profile-submit-btn">Update</button>
          </form>
          <form class="meals-profile tab-show" action="Controller" method="post">
            <h1>Meals</h1>
            <label for="mealtype">Meal Type</label>
            <label for="meal_price">Price</label>
            <br>
            <input type="hidden" name="command" value="register_meals"/>
            
            <c:choose>
              <c:when test="${not empty hotel.meals}">
                <c:forEach var="meals_entry" items="${hotel.meals}">
                  <input id="mealtype" type="text" name = "first_meal_type" value = "${meals_entry.key}" list="meal_types" />
                  <datalist id="meal_types">
                    <option value = "1">Breakfast</option>
                    <option value = "2">Half Board</option>
                    <option value = "3">Full Board</option>
                    <option value = "4">All Inclusive</option>
                    <option value = "5">None</option>
                  </datalist>
                  <input id="meal_price" type="number" name="first_meal_price" value="${meals_entry.value}" step="any" min="1">
                </c:forEach>
               </c:when>
               <c:when test="${empty hotel.meals}">

                 <input id="mealtype" type="text" name = "first_meal_type" list="meal_types1" />
                 <datalist id="meal_types1">
                   <option value = "1">Breakfast</option>
                   <option value = "2">Half Board</option>
                   <option value = "3">Full Board</option>
                   <option value = "4">All Inclusive</option>
                   <option value = "5">None</option>
                 </datalist>
                 <input id="meal_price" type="number" name="first_meal_price" value="" step="any" min="1">

                 <input id="mealtype" type="text" name = "second_meal_type" value ="" list="meal_types2" />
                 <datalist id="meal_types2">
                   <option value = "1">Breakfast</option>
                   <option value = "2">Half Board</option>
                   <option value = "3">Full Board</option>
                   <option value = "4">All Inclusive</option>
                   <option value = "5">None</option>
                 </datalist>
                <input id="meal_price" type="number" name="second_meal_price" value="" step="any" min="1">

                <input id="mealtype" type="text" name ="third_meal_type" value ="" list="meal_types3" />
          	    <datalist id="meal_types3">
           		  <option value = "1">Breakfast</option>
       		      <option value = "2">Half Board</option>
          		  <option value = "3">Full Board</option>
          		  <option value = "4">All Inclusive</option>
             	  <option value = "5">None</option>
                </datalist>
                <input id="meal_price" type="number" name="third_meal_price" value="" step="any" min="1">

                <input id="mealtype" type="text" name ="fourth_meal_type" value ="" list="meal_types4" />
                <datalist id="meal_types4">
       	          <option value = "1">Breakfast</option>
                  <option value = "2">Half Board</option>
                  <option value = "3">Full Board</option>
                  <option value = "4">All Inclusive</option>
                  <option value = "5">None</option>
                </datalist>
                <input id="meal_price" type="number" name="fourth_meal_price" value="" step="any" min="1">

                <input id="mealtype" type="text" name="fifth_meal_type" value ="" list="meal_types5" />
                <datalist id="meal_types5">
                  <option value = "1">Breakfast</option>
                  <option value = "2">Half Board</option>
                  <option value = "3">Full Board</option>
                  <option value = "4">All Inclusive</option>
                  <option value = "5">None</option>
                </datalist>
                <input id="meal_price" type="number" name="fifth_meal_price" value="" step="any" min="1">
              </c:when>
            </c:choose>
            <button type="submit" class="profile-submit-btn">Update</button>
          </form>
          <form class="hotel-parking-profile tab-show">
            <h1>Parking</h1>
            <label for="lots_number">Number of lots</label>
            <label for="parking-price">Price per lot</label>
            <br>
            <input type="number" class="profile-input" name="" value="" min="1" max="100">
            <input type="number" class="profile-input parking_price" name="" value="" step="any" min="0">
            <br>
            <button type="submit" class="profile-submit-btn">Update</button>
          </form>
        </div>
      </div>
    </div>
  
  	</c:when>
  </c:choose>
    
    
    
    
    
    <!-- FOOTER -->
    <footer>
      <div class="main-content">
        <div class="left box">
          <h2>${footer_about}</h2>
          <div class="content">
            <p>${footer_about_content}</p>
            <div class="social">
              <a href="#"><span class="fab fa-facebook-f"></span></a>
              <a href="#"><span class="fab fa-twitter"></span></a>
              <a href="#"><span class="fab fa-instagram"></span></a>
              <a href="#"><span class="fab fa-youtube"></span></a>
            </div>
          </div>
        </div>
        <div class="center box">
          <h2>${footer_address}</h2>
          <div class="content">
            <div class="place">
              <span class="fas fa-map-marker-alt"></span>
              <span class="text">${footer_address_content}</span>
            </div>
            <div class="phone">
              <span class="fas fa-phone-alt"></span>
              <span class="text">+37529-123-45-67</span>
            </div>
            <div class="email">
              <span class="fas fa-envelope"></span>
              <span class="text">contact@redwood.com</span>
            </div>
          </div>
        </div>
        <div class="right box">
          <h2>${footer_contact}</h2>
          <div class="content">
            <form id="contactUs">
              <input type="hidden" name="command" value="contact_us"/>
              <div class="email">
                <div class="text">Email *</div>
                <input type="email" required name="sender" >
              </div>
              <div class="msg">
                <div class="text">${footer_message}</div>
                <textarea name="text" rows="2" cols="25" required></textarea>
              </div>
              <div class="btn">
                <button id="submitButton" type="submit">${footer_submit}</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div class="bottom">
        <center>
          <span class="credit">Created By <a href="#">Maria Yerkovich</a></span>
          <span class="far fa-copyright"></span>
          <span>2020 All rights reserved.</span>
        </center>

      </div>
    </footer>
    
    <script type="text/javascript">
      $(function() {
        $("#contactUs").submit(function(e) {
          e.preventDefault(); 
          var $form = $(this);
          $.ajax({
            type: 'post',
            url: 'Controller',
            data: $form.serialize(),   // добавить сюда еще source : "ajax", чтоб не запоминался запрос и не было проблем с локализацией
            success: function(response) {
				if(response) {
					Swal.fire({
						  position: 'top-end',
						  icon: 'success',
						  title: 'Your message has been sent',
						  showConfirmButton: false,
						  timer: 2500
						})
				} else {
					Swal.fire({
						  icon: 'error',
						  title: 'An error occured',
						  text: 'Try to contact us later'
					})
				}		
			}
          })
        });
      });
    </script>
    
	<script src="./js/openform.js"> </script>
	<!-- ajax проверка email-a в базе -->
	<script type="text/javascript">
	$(document).ready(function() {
		var messages = document.getElementsByClassName("signup-messages")[0];
		$("#signup_email").on('change', function postinput() {
			messages.style.display = "none";
			var email = $(this).val();
			$.ajax({
				url:'Controller',
				data: {email : email,
				       command : "check_email_availability",
				       source : "ajax"
				       },
				type: 'get',
				success: function(response) {
					var json = $.parseJSON(response);
					console.log(json)
					if (!json) {
						messages.style.display = "block";
					} else {
						messages.style.display = "none";
					}
				}
			}) 
		})
	});

	</script>
	<script type="text/javascript">
      var today = new Date().toISOString().split('T')[0];
      document.getElementsByName("from")[0].setAttribute('min', today);
    </script>
    <script>
    document.getElementById("currentDate").addEventListener("change", function() {
      var input = this.value;
      var dateEntered = new Date(input);

      dateEntered.setDate(dateEntered.getDate()+1);

      var tomorrow = new Date(dateEntered).toISOString().split('T')[0];
      document.getElementsByName("till")[0].setAttribute('min', tomorrow); });
    </script>
    
    <script type="text/javascript">
       const tabBtn = document.querySelectorAll(".tab");
       const tab = document.querySelectorAll(".tab-show");

       function tabs(panelIndex) {
         tab.forEach(function(node) {
           node.style.display = "none";
         });
         tab[panelIndex].style.display = "block";
       }
       tabs(0);
     </script>
     <script type="text/javascript">
       $(".tab").click(function() {
         $(this).addClass("active").siblings().removeClass("active");
       })
     </script>
     <script>
      const wrapper = document.querySelector(".hotel-photo-profile-wrapper");
      const fileName = document.querySelector(".hotel-photo-profile-file-name");
      const defaultBtn = document.querySelector("#hotel-photo-profile-default-btn");
      const customBtn = document.querySelector("#hotel-photo-profile-custom-btn");
      const cancelBtn = document.querySelector("#hotel-photo-profile-cancel-btn i");
      const img = document.querySelector("img");
      let regExp = /[0-9a-zA-Z-а-яА-Я\^\&\'\@\{\}\[\]\,\$\=\!\-\#\(\)\.\%\+\~\_ ]+$/;

      
      
      defaultBtn.addEventListener("change", function(){
        const file = this.files[0];
        if(file){
          const reader = new FileReader();
          reader.onload = function(){
            const result = reader.result;
            img.src = result;
            wrapper.classList.add("active");
          }
          cancelBtn.addEventListener("click", function(){
            img.src = "";
            wrapper.classList.remove("active");
          })
          reader.readAsDataURL(file);
        }
        if(this.value){
          //let valueStore = this.value.match(regExp);
          fileName.textContent = valueStore;
        }
      });
      function defaultBtnActive(){
          defaultBtn.click();
        }
    </script>
</body>
</html>