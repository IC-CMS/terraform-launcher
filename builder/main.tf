provider "consul" {
  address 	= "127.0.0.1:8500"
  version	= "2.1.0"
}

provider "vault" {
  address	= "http://127.0.0.1:8200"
  token		= "6cd6b46e-ebe6-970a-9095-b0c8e6bbbc9a"
  version	= "1.1.0"
}

resource "consul_service" "jenkins" {
  name    = "jenkins"
  node    = "${consul_node.compute.name}"
  port    = 80
  tags    = ["tag0"]

}

resource "consul_node" "compute" {
  name    = "jenkins"
  address = "localhost"

}
