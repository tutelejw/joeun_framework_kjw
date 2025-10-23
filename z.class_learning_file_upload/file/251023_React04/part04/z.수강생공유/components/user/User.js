
import { Col, Container, Row } from 'react-bootstrap';
import User01 from './User01';
import User02 from './User02';
import User03 from './User03';

const User = () => {

  console.log("User");

  return (

      <Container>

        <Row>
          <Col lg={3}><User01/></Col>
          <Col lg={9}><User02/></Col>
        </Row>

        <User03/>

      </Container>

  );
};

export default User;