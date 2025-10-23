  

import { useParams } from 'react-router-dom';

const Purchase01 = () => {

console.log("Purchase01")

/*
URI: purchase/purchase01/TV/1000
Route: <Route path='/purchase/purchase01/:purchaseName/:price'>

URL Parameter TV, price 는
{ purchaseName: TV, price: 1000 } JSON 전달.
www.reactrouter.com: URL parameters 참조
*/

// Destructuring Assignment : 비구조화 할당 / 구조 분해 할당
let { purchaseName, price } = useParams();
console.log(purchaseName, price );

let params = useParams();
console.log(params);

return (
<div className="ViewGood">

<h2>Purchase01</h2>
Purchase01...
<br/><br/>
구매상품 : { purchaseName }
<br/>
구매상품 : { params.purchaseName }

<br/><br/>
구매가격 : { price }
<br/>
구매가격 : { params.price }

</div>
);
}

export default Purchase01;