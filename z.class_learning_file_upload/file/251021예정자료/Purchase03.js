

import { useLocation } from 'react-router-dom';

const Purchase03 = () => {

console.log("Purchase03")

/*
URI : /purchase/purchase03?purchaseName=TV&price=1000
Route : <Route path='/purchase/purchase03'>

URL QueryString TV 와 price 는
{ serarch : ?purchaseName=TV&price=1000 } 형식 JSON 전달.
www.reactrouter.com : Query parameters 참조
*/

// Destructuring Assignment : 비구조화 할당 / 구조분해 할당
const { search } = useLocation();
console.log(search);

// URLSearchParams : URL 의 QueryString 을 쉽게 다룰 수 있도록 제공하는 JS API.
// JS 내장 함수 사용 Pasing.
const abc = new URLSearchParams(search);
console.log(abc.get("purchaseName"));
console.log(abc.get("price"))

return (
<div className="ViewGood">

<h2>Purchase03</h2>
Purchase03...
<br/><br/>
구매상품 : { abc.get("purchaseName") }
<br/><br/>
구매가격 : { abc.get("price") }

</div>
);
}

export default Purchase03;