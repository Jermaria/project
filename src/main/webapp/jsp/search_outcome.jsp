<%@ taglib uri="custom_tag" prefix="ct"%>
<div class="selection">
    <div class="search_bar">
      <form class="booking_form_line">
        <div class="line_inputfield">
          <input type="text" class="input" name="" value="" placeholder="Country, city or hotel">
        </div>
        <div class="pair">
          <div class="line_inputfield ">
            <input class="datefield" type="date" name="currentDate" id="currentDate">
          </div>
          <div class="line_inputfield">
            <input class="datefield" type="date" name="nextDate" required>
          </div>
        </div>
        <div class="pair">
          <div class="line_inputfield">
            <label>Guests</label>
            <input type="number" value="1" class="number" min="1">
          </div>
          <div class="line_inputfield number_field">
            <label>Rooms</label>
            <input type="number" value="1" class="number" min="1">
          </div>
        </div>
        <div class="line_inputfield">
          <input type="submit" class="btn" value="search" id="slider">
        </div>
      </form>
    </div>

    <!--sidebar с критериями -->

    <div class="sidebar">
      <header>Filter</header>
      <form class="" action="index.html" method="post">
      <a class="active" href="#"><i class="fas fa-euro-sign"></i><span>Price Per Night</span>
        <div class="sidebar_hidden">
          <input type="checkbox" name="" value="" id="fifty">
          <label for="fifty">up to 50</label>
          <br>
          <input type="checkbox" name="" value="" id="fifty_hundred">
          <label for="fifty_hundred">50-100</label>
          <br>
          <input type="checkbox" name="" value="" id="over_100">
          <label for="over_100">over 100</label>
          <br>
          <p>Sort:</p>
          <input type="radio" name="" value="" id="sort_lth">
          <label for="sort_lth">low to high</label>
          <br>
          <input type="radio" name="" value="" id="sort_htl">
          <label for="sort_htl">high to low</label>
        </div>
      </a>
      <a class="star" href="#"><i class="far fa-star"></i><span>Star Rating</span>
        <div class="sidebar_hidden">
          <input type="checkbox" name="" value="" id="s_one">
          <label for="s_one">1*</label>
          <br>
          <input type="checkbox" name="" value="" id="s_two">
          <label for="s_two">2*</label>
          <br>
          <input type="checkbox" name="" value="" id="s_three">
          <label for="s_three">3*</label>
          <br>
          <input type="checkbox" name="" value="" id="s_four">
          <label for="s_one">4*</label>
          <br>
          <input type="checkbox" name="" value="" id="s_five">
          <label for="s_five">5*</label>
        </div>
      </a>
      <a class="feedback" href="#"><i class="far fa-smile"></i><span>Feedback Rating</span>
        <div class="sidebar_hidden">
        <div class="slider_center">
          <div class="rating_slider">
            <div class="show-value">
              <span id="current-value"> 5 </span>
            </div>
            <input type="range" id="custom-slider" name="" value="5" min="0" max="10" step="1">
            <div class="range">
              <div class="">0</div>
              <div class="">10</div>
            </div>
          </div>
        </div>
      </div>
      </a>
      <a class="meals" href="#"><i class="fas fa-utensils"></i><span>Meals</span>
        <div class="sidebar_hidden">
          <input type="checkbox" name="" value="" id="none">
          <label for="none">none</label>
          <br>
          <input type="checkbox" name="" value="" id="breakfast">
          <label for="breakfast">breakfast</label>
          <br>
          <input type="checkbox" name="" value="" id="hb">
          <label for="hb">HB</label>
          <br>
          <input type="checkbox" name="" value="" id="fb">
          <label for="fb">FB</label>
        </div>
      </a>
      <a class="facilities" href="#"><i class="fas fa-plus"></i><span>Facilities</span>
        <div class="sidebar_hidden">
          <input type="checkbox" name="" value="" id="wi-fi">
          <label for="wi-fi">WI-FI</label>
          <br>
          <input type="checkbox" name="" value="" id="parking">
          <label for="parking">parking</label>
          <br>
          <input type="checkbox" name="" value="" id="handicapped">
          <label for="handicapped">wheelchair access</label>
          <br>
        </div>
      </a>
      </form>
    </div>

    <!--список результатов поиска -->

    <div class="results_list">
      <div class="result-header">
        <h5 class="hotel-title">Hotel</h5>
        <h5 class="price">Price</h5>
        <h5 class="info">more info</h5>
      </div>
      <ct:search_results/>
      <!-- 
      <c:forEach var="hotel" items="${results_outcome}">
      <div class="item-line">
        <div class="options inline-bl">
          <span class="item_title">${hotel.name}</span>
          <p> Address line </p>
          <img class="pic" src="${hotel.photoPath[0]}">
          <span class="extra">Short description of this hotel stored only in html, not the database</span>
        </div>
        <div class="price item_price inline-bl"><c:out value="${hotel.roomFund.rooms['DOUBLE']}"/></div>
        <form class="btn_brief_form inline-bl" action="">
          <input class="info_btn" type="submit" value="More Info" />
        </form>
      </div>
      <hr />
	  </c:forEach>
	   -->

    </div>
  </div>